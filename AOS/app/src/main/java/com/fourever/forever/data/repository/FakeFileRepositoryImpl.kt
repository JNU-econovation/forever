package com.fourever.forever.data.repository

import com.fourever.forever.data.ResultWrapper
import com.fourever.forever.data.model.BaseResponse
import com.fourever.forever.data.model.request.GetGeneratedQuestionsRequestDto
import com.fourever.forever.data.model.request.GetGeneratedSummaryRequestDto
import com.fourever.forever.data.model.request.PostFileQuestionRequestDto
import com.fourever.forever.data.model.request.PostFileSummaryRequestDto
import com.fourever.forever.data.model.response.GetFileListResponseDto
import com.fourever.forever.data.model.response.GetFileQuestionResponseDto
import com.fourever.forever.data.model.response.GetFileSummaryResponseDto
import com.fourever.forever.data.model.response.GetGeneratedQuestionsResponseDto
import com.fourever.forever.data.model.response.GetGeneratedSummaryResponseDto
import com.fourever.forever.data.model.response.GetQuestionListDto
import com.fourever.forever.data.model.response.PostFileResponseDto
import com.fourever.forever.data.model.response.PostFileSummaryResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class FakeFileRepositoryImpl @Inject constructor() : FileRepository {
    override fun postFile(file: MultipartBody.Part): Flow<ResultWrapper<PostFileResponseDto>> {
        return flow {
            emit(
                ResultWrapper.Success(
                    PostFileResponseDto(
                        uploadFilePath = "uploads/assignment02.pdf_summary.txt",
                        fileName = "assignment02.pdf"
                    )
                )
            )
        }
    }

    override fun getGeneratedSummary(getGeneratedSummaryRequestDto: GetGeneratedSummaryRequestDto): Flow<ResultWrapper<GetGeneratedSummaryResponseDto>> {
        return flow {
            emit(
                ResultWrapper.Success(
                    GetGeneratedSummaryResponseDto(
                        filePath = "summaries/[01]강의개요 소개.pdf_summary.txt",
                        fileName = "[01]강의개요 소개.pdf",
                        summary = "1. 캡스톤 디자인 (Capstone Design)\\n캡스톤 디자인은 건축물의 정점에 놓인 장식을 의미하는 단어로, 최고의 업적과 성취를 상징합니다. 이 과목에서는 캡스톤 프로젝트를 수행하며, 강의 스케줄과 평가 방법에 대한 안내가 제공됩니다. 오리엔테이션을 시작으로 계획서 발표, 중간 발표, 최종 발표 및 보고서로 이뤄진 평가 과정이 안내되며, 학과 교수님들의 평가, 상대 팀 동료평가, 같은 팀 동료평가, 최종 보고서 등을 종합하여 평가합니다.\\n\\n2. 국내 학술대회 정보\\n소프트웨어 중심 대학사업단과 인공지능 혁신 융합 대학사업단에서 지원하는 국내 학술대회 정보가 안내됩니다. 2024년에 개최되는 한국 스마트미디어학회 춘계 학술대회, ASK (한국정보처리학회), 한국 컴퓨터종합학술대회 등의 대회 정보와 논문 제출 마감일, 논문등록비 및 출장비 지원 여부 등이 안내됩니다. 학생들은 이러한 학술대회에 참여하여 자신들의 연구 결과물을 발표할 수 있습니다.\\n\\n3. 프로젝트 평가 주안점\\n캡스톤 프로젝트의 평가 주안점에 대해 설명이 제공됩니다. 프로젝트 설계와 팀 협력을 중심으로 평가되며, 설계, 구현, 협력의 비중이 각각 어떻게 되는지 설명됩니다. 또한 팀원의 참여도, 맡은 역할 수행, 기여도 등을 종합적으로 평가하며, 주제에 대한 설계의 창의성과 기획성, 구현범위의 타당성, 팀 활동에 대한 성실성 등이 평가 항목으로 제시됩니다. 이를 통해 프로젝트에 대한 종합적인 평가가 이루어집니다."
                    )
                )
            )
        }
    }

    override fun getGeneratedQuestions(getGeneratedQuestionsRequestDto: GetGeneratedQuestionsRequestDto): Flow<ResultWrapper<List<GetGeneratedQuestionsResponseDto.QuestionAndAnswer>>> {
        return flow {
            emit(
                ResultWrapper.Success(
                    listOf(
                        GetGeneratedQuestionsResponseDto.QuestionAndAnswer(
                            "캡스톤 디자인 강의의 스케줄은 어떻게 구성되어 있는가?",
                            "오리엔테이션, 계획서 발표, 중간 발표, 최종 발표와 보고서로 구성되어 있음"
                        ),
                        GetGeneratedQuestionsResponseDto.QuestionAndAnswer(
                            "한국 스마트미디어학회 춘계학술대회와 ASK, 그리고 한국컴퓨터종합학술대회의 정보를 어디에서 확인할 수 있는가?",
                            "논문제출 마감일, 논문등록비, 출장비 지원사항 등의 정보는 각 대회의 공식 홈페이지에서 확인 가능함"
                        ),
                        GetGeneratedQuestionsResponseDto.QuestionAndAnswer(
                            "팀원 및 연구주제 선택에 대한 안내에는 어떠한 내용이 포함되어 있는가?",
                            "팀원 및 연구주제 선택 방법, 신청 기한, 희망지도교수 신청 방법, 산업체 멘토링 프로그램에 대한 설명이 포함되어 있음"
                        ),
                        GetGeneratedQuestionsResponseDto.QuestionAndAnswer(
                            "캡스톤 디자인 평가는 어디에서 이루어지는가?",
                            "학과 교수님들, 팀원, 최종보고서 등 다양한 측면에서 평가됨"
                        ),
                        GetGeneratedQuestionsResponseDto.QuestionAndAnswer(
                            "국내 학술대회 정보에는 어떤 사항이 안내되는가?",
                            "일시, 장소, 논문제출 마감일, 논문등록비, 출장비 지원사항 등의 정보가 안내됨"
                        ),
                    )
                )
            )
        }
    }

    override fun getFileList(page: Int): Flow<ResultWrapper<BaseResponse<GetFileListResponseDto>>> {
        return flow {
            emit(
                ResultWrapper.Success(
                    BaseResponse(
                        true,
                        null,
                        GetFileListResponseDto(
                            listOf(
                                GetFileListResponseDto.Document(1, "파일명1"),
                                GetFileListResponseDto.Document(2, "파일명1"),
                                GetFileListResponseDto.Document(3, "파일명1"),
                                GetFileListResponseDto.Document(4, "파일명1"),
                                GetFileListResponseDto.Document(5, "파일명1"),
                                GetFileListResponseDto.Document(6, "파일명1"),
                                GetFileListResponseDto.Document(7, "파일명1"),
                                GetFileListResponseDto.Document(8, "파일명1"),
                            )
                        )
                    )
                )
            )
        }
    }

    override fun postFileSummary(postFileSummaryRequestDto: PostFileSummaryRequestDto): Flow<ResultWrapper<BaseResponse<PostFileSummaryResponseDto>>> {
        return flow {
            emit(
                ResultWrapper.Success(
                    BaseResponse(
                        true,
                        null,
                        PostFileSummaryResponseDto(1)
                    )
                )
            )
        }
    }

    override fun postFileQuestion(
        documentId: Int,
        postFileQuestionRequestDto: PostFileQuestionRequestDto
    ): Flow<ResultWrapper<BaseResponse<Void>>> {
        return flow {
            emit(
                ResultWrapper.Success(
                    BaseResponse(
                        true,
                        null,
                        null
                    )
                )
            )
        }
    }

    override fun getFileSummary(documentId: Int): Flow<ResultWrapper<BaseResponse<GetFileSummaryResponseDto>>> {
        return flow {
            emit(
                ResultWrapper.Success(
                    BaseResponse(
                        true,
                        null,
                        GetFileSummaryResponseDto(
                            title = "프로그래밍 언어론_ch03a",
                            summary =
                            "필요성 및 기대효과 첫번째로, 필요성 및 기대효과입니다. 저희는 수많은 강의자료를 공부하던 도중 pdf 자료가 많고 정리가 힘들다는 점을 발견했습니다. 특히 시험기간이나 간단한 리마인딩을 할 때는 조금 부담스러운 면이 있다는 점에 주목했습니다. 따라서 이런 pdf를 활용해 정리해주고, 이를 기반으로 한 간단한 퀴즈들을 생성하여 대학생들의 학습을 보조해줄 수 있는 앱을 개발하고자 했습니다.\n" +
                                    "주요기능 저희는 효율적인 학습을 위해 필요한 기능을 회의를 통해 결정하였고, 크게 다음 세가지를 주요 기능으로 소개드리려고 합니다. 첫번째로는 강의 자료 요약입니다. 우리가 받는 pdf의 강의 자료를 AI를 통해 텍스트로 요약하여 제공합니다. 두번째로는 주요 내용에 관한 질문과 답변의 쌍을 만들어 공부에 도움을 줍니다. 그리고 마지막으로, 이 질문과 답변을 바탕으로 문제집을 생성하고, 이를 확인하며 복습할 수 있게 합니다. 팀 멤버로는 기획 이시현, 백엔드 곽민주, ai 박지유, aos 장현지가 있습니다.\n" +
                                    "저희 팀 멤버와 기술 스택은 다음과 같고요, 문서 요약 및 질문 답변 쌍 생성은 gpt api 를 이용하여 개발하고자 합니다 지금 저희 팀은 AICOSS의 ai 전공 심 필요성 및 기대효과 첫번째로, 필요성 및 기대효과입니다. 저희는 수많은 강의자료를 공부하던 도중 pdf 자료가 많고 정리가 힘들다는 점을 발견했습니다. 특히 시험기간이나 간단한 리마인딩을 할 때는 조금 부담스러운 면이 있다는 점에 주목했습니다. 따라서 이런 pdf를 활용해 정리해주고, 이를 기반으로 한 간단한 퀴즈들을 생성하여 대학생들의 학습을 보조해줄 수 있는 앱을 개발하고자 했습니다. 주요기능 저희는 효율적인 학습을 위해 필요한 기능을 회의를 통해 결정하였고, 크게 다음 세가지를 주요 기능으로 소개드리려고 합니다. 첫번째로는 강의 자료 요약입니다. 우리가 받는 pdf의 강의 자료를 AI를 통해 텍스트로 요약하여 제공합니다. 두번째로는 주요 내용에 관한 질문과 답변의 쌍을 만들어 공부에 도움을 줍니다. 그리고 마지막으로, 이 질문과 답변을 바탕으로 문제집을 생성하고, 이를 확인하며 복습할 수 있게 합니다. 팀 멤버로는 기획 이시현, 백엔드 곽민주, ai 박지유, aos 장현지가 있습니다. 저희 팀 멤버와 기술 스택은 다음과 같고요, 문서 요약 및 질문 답변 쌍 생성은 gpt api 를 이용하여 개발하고자 합니다. 지금 저희 팀은 AICOSS의 ai 전공 심다. 두번째로는 주요 내용에 관한 질문과 답변의 쌍을 만들어 공부에 도움을 줍니다. 그리고 마지막으로, 이 질문과 답변을 바탕으로 문제집을 생성하고, 이를 확인하며 복습할 수 있게 합니다. 팀 멤버로는 기획 이시현, 백엔드 곽민주, ai 박지유, aos 장현지가 있습니다. 저희 팀 멤버와 기술 스택은 다음과 같고요, 문서 요약 및 질문 답변 쌍 생성은 gpt api 를 이용하여 개발하고자 합니다. 지금 저희 팀은 AICOSS의 ai 전공 심고자 합니다.\n" +
                                    "지금 저희 팀은 AICOSS의 ai 전공 심고자 합니다. 팀은 AICOSS의 a"
                        )
                    )
                )
            )
        }
    }

    override fun getFileQuestionList(documentId: Int): Flow<ResultWrapper<BaseResponse<GetQuestionListDto>>> {
        return flow {
            emit(
                ResultWrapper.Success(
                    BaseResponse(
                        true,
                        null,
                        GetQuestionListDto(
                            listOf(
                                GetQuestionListDto.Questions(1, "질문1"),
                                GetQuestionListDto.Questions(2, "질문2"),
                                GetQuestionListDto.Questions(3, "질문3")
                            )
                        )
                    )
                )
            )
        }
    }

    override fun getFileQuestion(
        questionId: Int
    ): Flow<ResultWrapper<BaseResponse<GetFileQuestionResponseDto>>> {
        return flow {
            emit(
                ResultWrapper.Success(
                    BaseResponse(
                        true,
                        null,
                        GetFileQuestionResponseDto(
                            questionContent = "질문1",
                            answerContent = "답변1"
                        )
                    )
                )
            )
        }
    }

}