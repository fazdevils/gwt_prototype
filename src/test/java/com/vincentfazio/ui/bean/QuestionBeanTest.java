package com.vincentfazio.ui.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.bean.QuestionType;

public class QuestionBeanTest {

    @Test
    public void testTFQuestionWithSubquestion() {
        QuestionType questionType = QuestionType.TrueFalse;
        QuestionBean questionBean = createQuestion(questionType);
        ArrayList<QuestionBean> subquestions = new ArrayList<QuestionBean>();
        subquestions.add(createQuestion(QuestionType.TrueFalse));
        subquestions.add(createQuestion(QuestionType.TrueFalse));
        
        QuestionBean choice = questionBean.getChoices().get(0);
        choice.setAnsweredBy("test user");
        choice.setSubquestions(subquestions);
        
        assertFalse(questionBean.isAnswerCompleted());
        assertTrue(questionBean.hasSubquestions());
        QuestionBean.QuestionStatus status = questionBean.getSubquestionStatus();
        assertEquals(2, status.numberOfSubquestions);
        assertEquals(0, status.numberOfAnsweredSubquestions);
    }

    @Test
    public void testQuestionGroup() {
        QuestionType questionType = QuestionType.QuestionGroup;
        QuestionBean questionBean = createQuestion(questionType);
        
        assertFalse(questionBean.isAnswerCompleted());
        assertTrue(questionBean.hasSubquestions());
        QuestionBean.QuestionStatus status = questionBean.getSubquestionStatus();
        assertEquals(6, status.numberOfSubquestions);
        assertEquals(2, status.numberOfAnsweredSubquestions);
    }

    @Test
    public void testQuestionGroupWithSubgroups() {
        QuestionBean questionBean = new QuestionBean();
        questionBean.setQuestionType(QuestionType.QuestionGroup);
        questionBean.setSubquestions(new ArrayList<QuestionBean>());
        QuestionType questionType = QuestionType.QuestionGroup;
        questionBean.getSubquestions().add(createQuestion(questionType));
        questionBean.getSubquestions().add(createQuestion(questionType));
        
        assertFalse(questionBean.isAnswerCompleted());
        assertTrue(questionBean.hasSubquestions());
        QuestionBean.QuestionStatus status = questionBean.getSubquestionStatus();
        assertEquals(12, status.numberOfSubquestions);
        assertEquals(4, status.numberOfAnsweredSubquestions);
    }
    
    @Test
    public void testAnsweredChooseOneWithSubquestions() {
        QuestionType questionType = QuestionType.ChooseOne;
        QuestionBean questionBean = createQuestion(questionType);
        
        for (QuestionBean answerChoice: questionBean.getChoices()) {
            ArrayList<QuestionBean> subquestions = new ArrayList<QuestionBean>();
            QuestionBean subquestionBean = new QuestionBean();
            subquestionBean.setQuestionType(QuestionType.FillInTheBlank);
            subquestions.add(subquestionBean);
            answerChoice.setSubquestions(subquestions);
        }
        
        questionBean.getChoices().get(1).setAnswerValue("true");
        
        assertTrue(questionBean.isAnswered());
        assertFalse(questionBean.isAnswerCompleted());
        assertTrue(questionBean.hasSubquestions());
        QuestionBean.QuestionStatus status = questionBean.getSubquestionStatus();
        assertEquals(1, status.numberOfSubquestions);
        assertEquals(0, status.numberOfAnsweredSubquestions);
    }

    @Test
    public void testChooseOne() {
        QuestionType questionType = QuestionType.ChooseOne;
        QuestionBean questionBean = createQuestion(questionType);
        
        assertFalse(questionBean.isAnswerCompleted());
        assertFalse(questionBean.hasSubquestions());
        QuestionBean.QuestionStatus status = questionBean.getSubquestionStatus();
        assertEquals(0, status.numberOfSubquestions);
        assertEquals(0, status.numberOfAnsweredSubquestions);
    }

    private QuestionBean createQuestion(QuestionType questionType) {
        QuestionBean questionBean = new QuestionBean();
        questionBean.setQuestionType(questionType);
        switch (questionType) {
            case ChooseMany:
                ArrayList<QuestionBean> cmAnswerChoices = new ArrayList<QuestionBean>();
                cmAnswerChoices.add(new QuestionBean());
                cmAnswerChoices.add(new QuestionBean());
                questionBean.setChoices(cmAnswerChoices);
                break;
            case ChooseOne:
                ArrayList<QuestionBean> coAnswerChoices = new ArrayList<QuestionBean>();
                coAnswerChoices.add(new QuestionBean());
                coAnswerChoices.add(new QuestionBean());
                questionBean.setChoices(coAnswerChoices);
                break;
            case FillInTheBlank:
                questionBean.setAnswerValue("fibAnswer");
                break;
            case QuestionGroup:
                ArrayList<QuestionBean> subquestions = new ArrayList<QuestionBean>();
                subquestions.add(createQuestion(QuestionType.ChooseMany));
                subquestions.add(createQuestion(QuestionType.ChooseOne));
                subquestions.add(createQuestion(QuestionType.FillInTheBlank));
                subquestions.add(createQuestion(QuestionType.ReadOnly));
                subquestions.add(createQuestion(QuestionType.TrueFalse));
                questionBean.setSubquestions(subquestions);
                break;
            case ReadOnly:
                questionBean.setAnswerValue("roAnswer");
                break;
            case TrueFalse:
                ArrayList<QuestionBean> tfAnswerChoices = new ArrayList<QuestionBean>();
                QuestionBean choice;
                
                choice = new QuestionBean();
                choice.setQuestionId(questionBean.getQuestionId());
                choice.setQuestionText("yes");
                choice.setAnswerValue("true");
                tfAnswerChoices.add(choice);

                choice = new QuestionBean();
                choice.setQuestionId(questionBean.getQuestionId());
                choice.setQuestionText("no");
                choice.setAnswerValue("false");
                tfAnswerChoices.add(choice);
                
                questionBean.setChoices(tfAnswerChoices);
                break;
            default:
                break;
        }
        return questionBean;
    }

}
