package com.example.forever.service;

import com.example.forever.common.validator.DocumentAuthorizationValidator;
import com.example.forever.common.validator.DocumentValidator;
import com.example.forever.common.validator.QuestionAuthorizationValidator;
import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.validator.MemberValidator;
import com.example.forever.converter.DocumentConversion;
import com.example.forever.converter.QuestionConversion;
import com.example.forever.domain.Answer;
import com.example.forever.domain.Folder;
import com.example.forever.domain.Item;
import com.example.forever.domain.ItemType;
import com.example.forever.domain.Member;
import com.example.forever.domain.Question;
import com.example.forever.dto.SaveFolderRequest;
import com.example.forever.dto.document.request.DocumentSummaryRequest;
import com.example.forever.dto.document.request.DocumentUpdateRequest;
import com.example.forever.dto.document.request.SaveQuestionAnswerRequest;
import com.example.forever.dto.document.response.DocumentListResponse;
import com.example.forever.dto.document.response.DocumentSummaryResponse;
import com.example.forever.dto.document.response.EachDocumentResponse;
import com.example.forever.dto.document.response.EachQuestionResponse;
import com.example.forever.dto.document.response.GetSummaryResponse;
import com.example.forever.dto.document.response.QuestionAnswerResponse;
import com.example.forever.dto.document.response.QuestionListResponse;
import com.example.forever.exception.auth.NotResourceOwnerException;
import com.example.forever.exception.document.AnswerNotFoundException;
import com.example.forever.exception.document.QuestionNotFoundException;
import com.example.forever.exception.folder.FolderNotFoundException;
import com.example.forever.repository.AnswerRepository;
import com.example.forever.repository.DocumentRepository;
import com.example.forever.domain.Document;
import com.example.forever.repository.FolderRepository;
import com.example.forever.repository.ItemRepository;
import com.example.forever.repository.QuestionRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final MemberValidator memberValidator;
    private final DocumentValidator documentValidator;
    private final ItemRepository itemRepository;
    private final FolderRepository folderRepository;

    @Transactional
    public DocumentSummaryResponse saveDocumentSummary(DocumentSummaryRequest request, MemberInfo memberInfo) {
        Long memberId = memberInfo.getMemberId();
        // 1) 문서 작성자 검증
        Member member = memberValidator.validateAndGetById(memberId);

        // 2) 요청된 폴더가 루트(0)인지, 아니면 실제 폴더 조회
        Folder folder = null;
        if (request.folderId() != null && request.folderId() != 0L) {
            folder = folderRepository.findById(request.folderId())
                    .orElseThrow(FolderNotFoundException::new);

            // 3) 소유권 검증
            if (!folder.isOwnedBy(memberId)) {
                throw new NotResourceOwnerException();
            }
        }

        // 3. Document 생성 시 folder 주입
        Document savedDocument = documentRepository.save(
                Document.builder()
                        .title(request.title())
                        .summary(request.summary())
                        .member(member)
                        .folder(folder)        // root면 null
                        .build()
        );


        int newOrderValue = itemRepository.findMinOrderValue()
                .map(min -> min / 2)
                .orElse(0);

        // Item 생성 (파일)
        Item item = Item.builder()
                .type(ItemType.FILE)
                .refId(savedDocument.getId())
                .folder(folder)
                .orderValue(newOrderValue)
                .build();
        itemRepository.save(item);
        // 5. 응답
        return new DocumentSummaryResponse(savedDocument.getId());
    }

    public void createFolder(SaveFolderRequest request, MemberInfo memberInfo) {
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());
        Folder folder = folderRepository.save(
                Folder.builder().name(request.folderName()).createdBy(memberInfo.getMemberId()).build()
        );
        // 2. 최소 orderValue 조회
        Optional<Integer> minOrderOpt = itemRepository.findMinOrderValue();

        // 3. 새 orderValue 계산
        int newOrderValue = minOrderOpt.map(min -> min / 2).orElse(0); // 처음엔 0으로 시작

        Item item = Item.builder()
                .type(ItemType.FOLDER)
                .refId(folder.getId())
                .folder(null)
                .orderValue(newOrderValue)
                .build();
        itemRepository.save(item);
    }

    public void updateDocument(Long documentId, DocumentUpdateRequest request, MemberInfo memberInfo) {
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());
        Document document = documentValidator.validateAndGetById(documentId);
        DocumentAuthorizationValidator.validateAuthor(document, member);
        document.update(request.newName());
    }

    /**
     * 문서 삭제 (소프트 삭제)
     */
    @Transactional
    public void deleteDocument(Long documentId, MemberInfo memberInfo) {
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());
        Document document = documentValidator.validateAndGetById(documentId);
        DocumentAuthorizationValidator.validateAuthor(document, member);
        document.delete(); // 소프트 삭제 적용
    }


    public void saveDocumentQuestionAndAnswer(Long documentId, SaveQuestionAnswerRequest request,
                                              MemberInfo memberInfo) {
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());
        Document document = documentValidator.validateAndGetById(documentId);
        Question question = questionRepository.save(
                Question.builder().content(request.questionContent()).document(document).build()
        );
        DocumentAuthorizationValidator.validateAuthor(document, member);
        answerRepository.save(
                Answer.builder().content(request.answerContent()).question(question).build()
        );
    }


    public GetSummaryResponse getDocumentSummary(Long documentId, MemberInfo memberInfo) {
        System.out.println("멤버아이디" + memberInfo.getMemberId());

        memberValidator.validateExistence(memberInfo.getMemberId());
        Document document = documentValidator.validateAndGetById(documentId);

        return new GetSummaryResponse(document.getTitle(), document.getSummary());

    }

    public QuestionListResponse getQuestionList(Long documentId, MemberInfo memberInfo) {
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());
        Document document = documentValidator.validateAndGetById(documentId);
        //문서 소유자 검증
        DocumentAuthorizationValidator.validateAuthor(document, member);
        List<Question> questionList = questionRepository.findAllByDocumentId(documentId);
        List<EachQuestionResponse> responses = questionList.stream()
                .map(QuestionConversion::convertToEachQuestionResponse)
                .toList();

        return new QuestionListResponse(responses);
    }


    public QuestionAnswerResponse getQuestionAndAnswer(Long questionId, MemberInfo memberInfo) {
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));
        QuestionAuthorizationValidator.validateMemberAccessToQuestion(question, member);
        Answer answer = answerRepository.findByQuestionId(questionId)
                .orElseThrow(() -> new AnswerNotFoundException(questionId));

        return new QuestionAnswerResponse(question.getContent(), answer.getContent());
    }

    //여기서 조회를 2번해서 가져올 수 있지만 서로 연관되어있는 question, answer를 한번에 가져오기 위해 join fetch를 사용하여 조회를 한번만 하도록
    //하지만 이렇게 하면 questionId의 값이 없을 경우에는 예외가 발생하게 되므로 Optional로 감싸서 반환하도록

//    //TODO : 안쓰는 메서드
//    public DocumentListResponse getDocumentList(Long pageId, MemberInfo memberInfo) {
//        memberValidator.validateExistence(memberInfo.getMemberId());
//        Pageable pageable = PageRequest.of(pageId.intValue(), 10, Sort.by("id").descending());
//        Page<Document> page = documentRepository.findAll(pageable);
//
//        List<Document> documentList = page.getContent();
//
//        List<EachDocumentResponse> responses = documentList.stream()
//                .map(DocumentConversion::convertToEachDocumentResponse)
//                .toList();
//
//        return new DocumentListResponse(responses);
//    }

}

