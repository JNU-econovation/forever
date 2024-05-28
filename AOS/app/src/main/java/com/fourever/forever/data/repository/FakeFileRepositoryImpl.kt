package com.fourever.forever.data.repository

import com.fourever.forever.data.ResultWrapper
import com.fourever.forever.data.model.BaseResponse
import com.fourever.forever.data.model.request.PostFileQuestionRequestDto
import com.fourever.forever.data.model.request.PostFileSummaryRequestDto
import com.fourever.forever.data.model.response.GetFileListResponseDto
import com.fourever.forever.data.model.response.GetFileQuestionResponseDto
import com.fourever.forever.data.model.response.GetFileSummaryResponseDto
import com.fourever.forever.data.model.response.GetQuestionListDto
import com.fourever.forever.data.model.response.PostFileSummaryResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class FakeFileRepositoryImpl @Inject constructor() : FileRepository {
    override fun postFile(file: MultipartBody.Part): Flow<ResultWrapper<Unit>> {
        TODO("Not yet implemented")
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