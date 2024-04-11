package com.fourever.forever.presentation.getsummary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourever.forever.R
import com.fourever.forever.presentation.SCREEN_MARGIN
import com.fourever.forever.presentation.component.btmsheet.BTM_SHEET_PEEK_HEIGHT
import com.fourever.forever.presentation.component.btmsheet.BTM_SHEET_RADIUS
import com.fourever.forever.presentation.component.btmsheet.QuestionListBtnSheet
import com.fourever.forever.ui.theme.foreverTypography

private const val SPACE_BETWEEN_TITLE_AND_CONTENT = 50
private const val CONTENT_AREA_HEIGHT = 550

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetSummaryScreen(fileName: String, summary: String) {
    BottomSheetScaffold(
        sheetContent = {
            Column(
                modifier = Modifier.padding(horizontal = SCREEN_MARGIN.dp)
            ) {
                QuestionListBtnSheet()
            }
        },
        sheetPeekHeight = BTM_SHEET_PEEK_HEIGHT.dp,
        sheetShape = RoundedCornerShape(
            topStart = BTM_SHEET_RADIUS.dp,
            topEnd = BTM_SHEET_RADIUS.dp
        ),
        sheetContainerColor = colorResource(id = R.color.white)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.purple_light),
                            colorResource(id = R.color.blue_light),
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SCREEN_MARGIN.dp)
            ) {
                Column {
                    Text(
                        text = fileName,
                        style = foreverTypography.titleLarge,
                        color = colorResource(id = R.color.paragraph)
                    )
                    Text(
                        text = stringResource(id = R.string.summary_subtitle),
                        style = foreverTypography.bodyLarge,
                        color = colorResource(id = R.color.paragraph)
                    )
                }
                Spacer(modifier = Modifier.size(SPACE_BETWEEN_TITLE_AND_CONTENT.dp))
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .height(
                            height = CONTENT_AREA_HEIGHT.dp
                        )
                ) {
                    Text(
                        text = summary,
                        style = foreverTypography.bodyMedium,
                        color = colorResource(id = R.color.paragraph)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
private fun SummaryPreview() {
    MaterialTheme {
        GetSummaryScreen(
            "프로그래밍 언어론_ch03a", "" +
                    "필요성 및 기대효과\n" +
                    "첫번째로, 필요성 및 기대효과입니다. 저희는 수많은 강의자료를 공부하던 도중 pdf 자료가 많고 정리가 힘들다는 점을 발견했습니다. 특히 시험기간이나 간단한 리마인딩을 할 때는 조금 부담스러운 면이 있다는 점에 주목했습니다.\n" +
                    "따라서 이런 pdf를 활용해 정리해주고, 이를 기반으로 한 간단한 퀴즈들을 생성하여 대학생들의 학습을 보조해줄 수 있는 앱을 개발하고자 했습니다.\n" +
                    "주요기능\n" +
                    "저희는 효율적인 학습을 위해 필요한 기능을 회의를 통해 결정하였고, 크게 다음 세가지를 주요 기능으로 소개드리려고 합니다.\n" +
                    "첫번째로는 강의 자료 요약입니다. 우리가 받는 pdf의 강의 자료를 AI를 통해 텍스트로 요약하여 제공합니다.\n" +
                    "두번째로는 주요 내용에 관한 질문과 답변의 쌍을 만들어 공부에 도움을 줍니다.\n" +
                    "그리고 마지막으로, 이 질문과 답변을 바탕으로 문제집을 생성하고, 이를 확인하며 복습할 수 있게 합니다.\n" +
                    "팀 멤버로는 기획 이시현, 백엔드 곽민주, ai 박지유, aos 장현지가 있습니다.\n" +
                    "저희 팀 멤버와 기술 스택은 다음과 같고요, 문서 요약 및 질문 답변 쌍 생성은 gpt api 를 이용하여 개발하고자 합니다.\n" +
                    "지금 저희 팀은 AICOSS의 ai 전공 심"
        )
    }
}