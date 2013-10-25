package com.vincentfazio.ui.survey.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.survey.activity.SecuritySurveyQuestionActivity;
import com.vincentfazio.ui.survey.activity.SecuritySurveyQuestionSaveActivity;
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.survey.view.component.ChooseManyButtonComponent;
import com.vincentfazio.ui.survey.view.component.ChooseOneRadioButtonComponent;
import com.vincentfazio.ui.survey.view.component.TrueFalseButtonComponent;
import com.vincentfazio.ui.view.component.ValidationTextBox;

public class SecuritySurveyQuestionView extends Composite implements SecuritySurveyQuestionDisplay {

    private static SecuritySurveyQuestionViewUiBinder uiBinder = GWT.create(SecuritySurveyQuestionViewUiBinder.class);

    interface SecuritySurveyQuestionViewUiBinder extends UiBinder<Widget, SecuritySurveyQuestionView> {}

    @UiField
    Label questionId;
    
    @UiField
    DivElement saveDiv;

    @UiField
    Anchor undoButton;
    
    @UiField
    Button saveButton;
    
    @UiField
    FlowPanel questionPanel;
    
    private String companyId = null;
    private QuestionBean question = null;
    private ArrayList<ValidationTextBox> validationQuestions = new ArrayList<ValidationTextBox>();
    private boolean hasUnsavedChanges;
    private DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy"); 


    public SecuritySurveyQuestionView() {

        initWidget(uiBinder.createAndBindUi(this));

        undoButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                new SecuritySurveyQuestionActivity(companyId, question, GwtSurveyGlobals.getInstance()).start(null, null);
            }
        });
        
        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                new SecuritySurveyQuestionSaveActivity(companyId, getQuestion(), GwtSurveyGlobals.getInstance()).start(null, null);
            }
        });       
        
        GwtSurveyGlobals.getInstance().registerDisplay(SecuritySurveyQuestionDisplay.class, this);
        
    }
    
    
    @Override
    public QuestionBean getQuestion() {
        return question;
    }


    @Override
    public void setQuestion(String companyId, QuestionBean question) {
        this.companyId = companyId;  
        this.question = question;
        questionId.setText(question.getQuestionId());
        
        resetQuestionView();
        createQuestionDisplay(questionPanel, question);
    }


    private void resetQuestionView() {
        setHasUnsavedChanges(false);
        validationQuestions.clear();
        questionPanel.clear();
    }
    
    
    private void createQuestionDisplay(final Panel questionPanel, final QuestionBean questionBean) {
        Label questionLabel = new Label(questionBean.getQuestionText());
        questionLabel.setStyleName("gwt-form-group-label");
        questionPanel.add(questionLabel);
        
        FlowPanel answerPanel = new FlowPanel();
        answerPanel.setStyleName("gwt-survey-answer");
        questionPanel.add(answerPanel);
        
        List<QuestionBean> subquestions = questionBean.getSubquestions();

        switch (questionBean.getQuestionType()) {
            case FillInTheBlank:
                final ValidationTextBox textBox = new ValidationTextBox();
                textBox.addStyleName("gwt-answer-text");
                textBox.setText(questionBean.getAnswerValue());
                textBox.addKeyUpHandler(new KeyUpHandler() {                    
                    @Override
                    public void onKeyUp(KeyUpEvent event) {
                        setHasUnsavedChanges(true);
                        questionBean.setAnswerValue(textBox.getValue());
                     }
                });
                answerPanel.add(textBox);
                validationQuestions.add(textBox);
                break;
            case TextBox:
                final TextArea textArea = new TextArea();
                textArea.addStyleName("gwt-answer-text");
                textArea.setText(questionBean.getAnswerValue());
                textArea.addKeyUpHandler(new KeyUpHandler() {                    
                    @Override
                    public void onKeyUp(KeyUpEvent event) {
                        setHasUnsavedChanges(true);
                        questionBean.setAnswerValue(textArea.getValue());
                     }
                });
                answerPanel.add(textArea);
                break;
            case Date:
                final DateBox date = new DateBox();
                date.setFormat(new DateBox.Format() {
                    @Override
                    public void reset(DateBox dateBox, boolean abandon) {
                    }
                    
                    @Override
                    public Date parse(DateBox dateBox, String text, boolean reportError) {
                        if ((null == text) || text.isEmpty()) {
                            return null;
                        }
                        return dateFormat.parse(text);
                    }
                    
                    @Override
                    public String format(DateBox dateBox, Date date) {
                        if (null == date) {
                            return null;
                        }
                        return dateFormat.format(date);
                    }
                });
                date.addStyleName("gwt-answer-text");
                String dateString = questionBean.getAnswerValue();
                if (null != dateString) {
                    date.setValue(dateFormat.parse(dateString));
                }
                date.addValueChangeHandler(new ValueChangeHandler<Date>() {            
                    @Override
                    public void onValueChange(ValueChangeEvent<Date> event) {
                        setHasUnsavedChanges(true);
                        questionBean.setAnswerValue(dateFormat.format(date.getValue()));
                     }
                });
                answerPanel.add(date);
                break;
            case ReadOnly:
                TextBox readOnlyTextBox = new TextBox();
                readOnlyTextBox.addStyleName("gwt-answer-text");
                readOnlyTextBox.setText(questionBean.getAnswerValue());
                readOnlyTextBox.setReadOnly(true);
                answerPanel.add(readOnlyTextBox);
                break;
            case ChooseOne:
                for (QuestionBean answerChoice: questionBean.getChoices()) {
                    ChooseOneRadioButtonComponent chooseOneComponent = new ChooseOneRadioButtonComponent(questionBean, answerChoice);
                    chooseOneComponent.addStyleName("gwt-answer-radio");
                    chooseOneComponent.addValueChangeHandler(new ValueChangeHandler<Boolean>() {                        
                        @Override
                        public void onValueChange(ValueChangeEvent<Boolean> event) {
                            setHasUnsavedChanges(true);
                            ChooseOneRadioButtonComponent selectedButton = (ChooseOneRadioButtonComponent) event.getSource();
                            QuestionBean chooseOneQuestion = selectedButton.getQuestion();
                            QuestionBean selectedChoice = selectedButton.getAnswerChoice();
                            for (QuestionBean choice: chooseOneQuestion.getChoices()) {
                                Boolean isSelected = choice.equals(selectedChoice);
                                choice.setAnswerValue(isSelected.toString());
                            }
                            questionPanel.clear();
                            createQuestionDisplay(questionPanel, questionBean);
                        }
                    });
                    answerPanel.add(chooseOneComponent);
                }
                QuestionBean selectedChoice = questionBean.getSelectedChoice();
                if (null != selectedChoice) {
                    subquestions = selectedChoice.getSubquestions();
                }
                break;
            case ChooseMany:
                for (final QuestionBean answerChoice: questionBean.getChoices()) {
                    ChooseManyButtonComponent chooseManyComponent = new ChooseManyButtonComponent(answerChoice);
                    chooseManyComponent.addStyleName("gwt-answer-toggle");
                    chooseManyComponent.addClickHandler(new ClickHandler() {                        
                        @Override
                        public void onClick(ClickEvent event) {
                            setHasUnsavedChanges(true);
                            questionPanel.clear();
                            createQuestionDisplay(questionPanel, questionBean);
                        }
                    });
                    answerPanel.add(chooseManyComponent);
                }
                break;
            case TrueFalse:
                TrueFalseButtonComponent tfButtonComponent = new TrueFalseButtonComponent(questionBean);
                tfButtonComponent.addStyleName("gwt-answer-toggle");
                tfButtonComponent.addClickHandler(new ClickHandler() {                        
                    @Override
                    public void onClick(ClickEvent event) {
                        setHasUnsavedChanges(true);
                        questionPanel.clear();
                        createQuestionDisplay(questionPanel, questionBean);
                    }
                });
                answerPanel.add(tfButtonComponent);
                QuestionBean tfSelectedChoice = questionBean.getSelectedChoice();
                if (null != tfSelectedChoice) {
                    subquestions = tfSelectedChoice.getSubquestions();
                }
                break;
            default:
                break;
        }
        /*
        Label debugLabel = new Label("wrap=" + questionLabel.getWordWrap() + ";  height=" + questionLabel.getOffsetHeight());
        questionPanel.add(debugLabel);
         */

        // if there are subquestions
        if (null != subquestions) {
            for (QuestionBean subquestion: subquestions) {
                FlowPanel subquestionPanel = new FlowPanel();
                subquestionPanel.setStyleName("gwt-form-group-area");
                questionPanel.add(subquestionPanel);
                createQuestionDisplay(subquestionPanel, subquestion);
            }
        }
    }

    @Override
    public boolean isValid() {
        boolean isValid = true;
        for (ValidationTextBox validationQuestion: validationQuestions) {
            isValid = isValid && validationQuestion.isValid();
        }
        return isValid;
    }
    
    @Override
    public boolean hasUnsavedChanges() {
        return hasUnsavedChanges;
    }


    private void setHasUnsavedChanges(boolean hasChanges) {
        hasUnsavedChanges = hasChanges;
        Style divStyle = saveDiv.getStyle();
        if (hasChanges) {
            divStyle.setDisplay(Display.BLOCK);            
            saveButton.setEnabled(isValid());
        } else {
            divStyle.setDisplay(Display.NONE);
        }
    }

}
