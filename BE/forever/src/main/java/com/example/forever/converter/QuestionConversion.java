package com.example.forever.converter;

import com.example.forever.domain.Question;
import com.example.forever.dto.document.response.EachQuestionResponse;

public class QuestionConversion {
    public static EachQuestionResponse convertToEachQuestionResponse(Question question) {
        return new EachQuestionResponse(
                question.getId(),
                question.getContent()
        );
    }
}