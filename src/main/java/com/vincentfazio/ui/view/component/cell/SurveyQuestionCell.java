package com.vincentfazio.ui.view.component.cell;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.view.client.SelectionModel;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.bean.QuestionType;
import com.vincentfazio.ui.bean.QuestionBean.QuestionStatus;

public class SurveyQuestionCell extends AbstractCell<QuestionBean> {

    final SelectionModel<QuestionBean> selectionModel;
    
    public SurveyQuestionCell(SelectionModel<QuestionBean> selectionModel) {
        this.selectionModel = selectionModel;
    }

    @Override
    public void render(
            com.google.gwt.cell.client.Cell.Context context, 
            QuestionBean question, 
            SafeHtmlBuilder sb) 
    {
        if (question == null) {
            return;
        }

        sb.appendHtmlConstant("<div class=\"-survey-question\">");
        boolean isSelected = selectionModel.isSelected(question);
        if (isSelected) {
            if (question.isAnswerCompleted()) {
                sb.appendHtmlConstant("<div class=\"selected-answered\">");
            } else {
                sb.appendHtmlConstant("<div class=\"selected-unanswered\">");
            }
        } else {
            if (question.isAnswerCompleted()) {
                sb.appendHtmlConstant("<div class=\"answered\">");
            } else {
                sb.appendHtmlConstant("<div class=\"unanswered\">");
            }
        }

        sb.appendHtmlConstant("<div class=\"question\">");
        SafeHtml safeHtmlQuestion;
        switch (question.getQuestionType()) {
            case QuestionGroup:
            case ChooseMany:
            case ChooseOne:
                safeHtmlQuestion = SafeHtmlUtils.fromString(question.getQuestionId());
                break;
             default:
                safeHtmlQuestion = SafeHtmlUtils.fromString(question.getQuestionId());
                break;
        }
        sb.append(safeHtmlQuestion);
        sb.appendHtmlConstant("</div>");

        if (question.isAnswered()) {
            sb.appendHtmlConstant("<div class=\"answer\">");
            SafeHtml safeHtmlAnswer = SafeHtmlUtils.fromString(question.getAnswerValue());
            sb.append(safeHtmlAnswer);
            sb.appendHtmlConstant("</div>");            
        }
        
        sb.appendHtmlConstant("<div class=\"subquestion-status\">");
        if (question.hasSubquestions() || (question.getQuestionType() == QuestionType.ChooseMany)) {
            QuestionBean.QuestionStatus subquestionStatus = question.getSubquestionStatus();
            if (0 != subquestionStatus.numberOfSubquestions) {
                String subquestionStatusString = subquestionStatus.numberOfAnsweredSubquestions + " of " + subquestionStatus.numberOfSubquestions + " questions answered";
                SafeHtml safeHtmlAnswer = SafeHtmlUtils.fromString(subquestionStatusString);
                sb.append(safeHtmlAnswer);
            }
        } else if (!question.isAnswered()) {
            if (!isSelected) {
                SafeHtml safeHtmlAnswer = SafeHtmlUtils.fromString("Click to answer...");
                sb.append(safeHtmlAnswer);
            }
        }
        sb.appendHtmlConstant("</div>");            

        sb.appendHtmlConstant("</div>");        
        sb.appendHtmlConstant("</div>");        

        sb.appendHtmlConstant("<div class=\"debug\">");
        sb.appendEscaped("Question Type:" + question.getQuestionType().toString()); // temporary
        sb.appendHtmlConstant("</div>");        
        sb.appendHtmlConstant("<div class=\"debug\">");
        QuestionStatus questionStatus = question.getQuestionStatus();
        sb.appendEscaped(questionStatus.numberOfAnsweredSubquestions + " of " + questionStatus.numberOfSubquestions + " completed");
        sb.appendHtmlConstant("</div>");        

    }

}
