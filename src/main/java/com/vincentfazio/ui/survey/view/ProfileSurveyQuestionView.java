package com.vincentfazio.ui.survey.view;

import java.util.ArrayList;
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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.survey.activity.ProfileSurveyQuestionActivity;
import com.vincentfazio.ui.survey.activity.ProfileSurveyQuestionSaveActivity;
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.survey.view.component.ChooseManyButtonComponent;
import com.vincentfazio.ui.survey.view.component.ChooseOneRadioButtonComponent;
import com.vincentfazio.ui.survey.view.component.TrueFalseButtonComponent;
import com.vincentfazio.ui.view.component.DollarValidationTextBox;
import com.vincentfazio.ui.view.component.FloatValidationTextBox;
import com.vincentfazio.ui.view.component.IntegerValidationTextBox;
import com.vincentfazio.ui.view.component.PercentValidationTextBox;
import com.vincentfazio.ui.view.component.ValidationTextBox;
import com.vincentfazio.ui.view.component.YearValidationTextBox;

public class ProfileSurveyQuestionView extends Composite implements ProfileSurveyQuestionDisplay {

    private static ProfileSurveyQuestionViewUiBinder uiBinder = GWT.create(ProfileSurveyQuestionViewUiBinder.class);

    interface ProfileSurveyQuestionViewUiBinder extends UiBinder<Widget, ProfileSurveyQuestionView> {}

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
    

    public ProfileSurveyQuestionView() {

        initWidget(uiBinder.createAndBindUi(this));

        undoButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                new ProfileSurveyQuestionActivity(companyId, question, GwtSurveyGlobals.getInstance()).start(null, null);
            }
        });
        
        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                new ProfileSurveyQuestionSaveActivity(companyId, getQuestion(), GwtSurveyGlobals.getInstance()).start(null, null);
            }
        });       
        
        GwtSurveyGlobals.getInstance().registerDisplay(ProfileSurveyQuestionDisplay.class, this);
        
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
                final ValidationTextBox textBox = createFillInTheBlankQuestionDisplay(questionBean);
                answerPanel.add(textBox);
                validationQuestions.add(textBox);
                break;
            case ReadOnly:
                TextBox readOnlyTextBox = createReadOnlyQuestionDisplay(questionBean);
                answerPanel.add(readOnlyTextBox);
                break;
            case ChooseOne:
                for (QuestionBean answerChoice: questionBean.getChoices()) {
                    ChooseOneRadioButtonComponent chooseOneComponent = createChooseOneQuestionDisplay(questionPanel, questionBean, answerChoice);
                    answerPanel.add(chooseOneComponent);
                }
                QuestionBean selectedChoice = questionBean.getSelectedChoice();
                if (null != selectedChoice) {
                    subquestions = selectedChoice.getSubquestions();
                }
                break;
            case ChooseMany:
                for (final QuestionBean answerChoice: questionBean.getChoices()) {
                    ChooseManyButtonComponent chooseManyComponent = createChooseManyQuestionDisplay(questionPanel, questionBean, answerChoice);
                    answerPanel.add(chooseManyComponent);
                }
                break;
            case TrueFalse:
                TrueFalseButtonComponent tfButtonComponent = createTrueFalseQuestionDisplay(questionPanel, questionBean);
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


    private TrueFalseButtonComponent createTrueFalseQuestionDisplay(
            final Panel questionPanel, final QuestionBean questionBean) {
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
        return tfButtonComponent;
    }


    private ChooseManyButtonComponent createChooseManyQuestionDisplay(
            final Panel questionPanel, 
            final QuestionBean questionBean,
            final QuestionBean answerChoice) 
    {
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
        return chooseManyComponent;
    }


    private ChooseOneRadioButtonComponent createChooseOneQuestionDisplay(
            final Panel questionPanel, 
            final QuestionBean questionBean,
            QuestionBean answerChoice) 
    {
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
        return chooseOneComponent;
    }


    private ValidationTextBox createFillInTheBlankQuestionDisplay(
            final QuestionBean questionBean) 
    {
        final ValidationTextBox textBox;
        String validationType = questionBean.getValidationType();
        if (null == validationType) {
            textBox = new ValidationTextBox();        	
        } else {
			if (validationType.equals("dollar")) {
	            textBox = new DollarValidationTextBox();
	        } else if (validationType.equals("integer")) {
	            textBox = new IntegerValidationTextBox();
	        } else if (validationType.equals("percent")) {
	            textBox = new PercentValidationTextBox();
	        } else if (validationType.equals("year")) {
	            textBox = new YearValidationTextBox();
	        } else if (validationType.equals("float")) {
	            textBox = new FloatValidationTextBox();
	        } else {
	            textBox = new ValidationTextBox();
	        }
        }
        textBox.addStyleName("gwt-answer-text");
        textBox.setText(questionBean.getAnswerValue());
        textBox.addKeyUpHandler(new KeyUpHandler() {                    
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
                questionBean.setAnswerValue(textBox.getValue());
             }
        });
        return textBox;
    }


    private TextBox createReadOnlyQuestionDisplay(final QuestionBean questionBean) {
        TextBox readOnlyTextBox = new TextBox();
        readOnlyTextBox.addStyleName("gwt-answer-text");
        readOnlyTextBox.setText(questionBean.getAnswerValue());
        readOnlyTextBox.setReadOnly(true);
        return readOnlyTextBox;
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
