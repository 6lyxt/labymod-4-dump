// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.title.ui;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.labynet.model.SurveyVoteResult;
import net.labymod.api.util.io.web.result.Result;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.core.labynet.model.SurveyOption;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.core.labynet.DefaultLabyNetController;
import net.labymod.core.labynet.model.Survey;
import java.util.List;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@AutoWidget
public class SurveysWidget extends VerticalListWidget<Widget>
{
    private final List<Survey> surveys;
    private final DefaultLabyNetController labyNetController;
    private final Pressable closeAction;
    private final Pressable submitAction;
    
    public SurveysWidget(final List<Survey> surveys, final DefaultLabyNetController labyNetController, final Pressable closeAction, final Pressable submitAction) {
        this.addId("survey-wrapper");
        this.surveys = surveys;
        this.labyNetController = labyNetController;
        this.closeAction = closeAction;
        this.submitAction = submitAction;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final Survey survey = this.getSurvey();
        if (survey == null) {
            return;
        }
        final VerticalListWidget<Widget> surveyWidget = new VerticalListWidget<Widget>();
        surveyWidget.addId("survey");
        if (survey.hasParticipated()) {
            surveyWidget.addId("survey-voted");
        }
        final DivWidget titleWrapper = new DivWidget();
        titleWrapper.addId("survey-title-wrapper");
        final ComponentWidget title = ComponentWidget.component(Component.translatable("labymod.survey.title", new Component[0]));
        title.addId("survey-title");
        ((AbstractWidget<ComponentWidget>)titleWrapper).addChild(title);
        final IconWidget closeIcon = new IconWidget(Textures.SpriteCommon.WHITE_X);
        closeIcon.addId("survey-close");
        closeIcon.setPressable(() -> {
            this.closeAction.press();
            this.submitAction.press();
            return;
        });
        ((AbstractWidget<IconWidget>)titleWrapper).addChild(closeIcon);
        surveyWidget.addChild(titleWrapper);
        final ComponentWidget question = ComponentWidget.component(Component.text(survey.getQuestion()));
        question.addId("survey-question");
        surveyWidget.addChild(question);
        if (survey.getImageUrl() != null) {
            final DivWidget imageWrapper = new DivWidget();
            imageWrapper.addId("survey-image-wrapper");
            final IconWidget image = new IconWidget(Icon.url(survey.getImageUrl()));
            image.addId("survey-image");
            ((AbstractWidget<IconWidget>)imageWrapper).addChild(image);
            surveyWidget.addChild(imageWrapper);
        }
        switch (survey.getType()) {
            case SINGLE_CHOICE:
            case MULTIPLE_CHOICE: {
                for (final SurveyOption answer : survey.getOptions()) {
                    final TextComponent text = Component.text(answer.getText());
                    if (survey.hasParticipated()) {
                        final int voteCount = answer.getVoteCount();
                        if (answer.isVoted()) {
                            text.color(NamedTextColor.GREEN);
                            text.append(Component.icon(Textures.SpriteCommon.GREEN_CHECKED));
                        }
                        text.append(((BaseComponent<Component>)Component.text(" (")).color(NamedTextColor.GRAY)).append(((BaseComponent<Component>)Component.text(voteCount)).color(NamedTextColor.GRAY)).append(((BaseComponent<Component>)Component.text(")")).color(NamedTextColor.GRAY));
                    }
                    final ButtonWidget surveyButton = ButtonWidget.component(text);
                    if (answer.isVoted()) {
                        ((AbstractWidget<Widget>)surveyButton).addId("survey-button-voted");
                    }
                    ((AbstractWidget<Widget>)surveyButton).addId("survey-button");
                    surveyButton.setPressable(() -> this.labyNetController.sendSurveyAnswer(survey, answer.getId(), null, result -> result.ifPresent(surveyAnswer -> {
                        if (!(!surveyAnswer.isSuccess())) {
                            survey.getOptions().iterator();
                            final Iterator iterator2;
                            while (iterator2.hasNext()) {
                                final SurveyOption a = iterator2.next();
                                a.setVoted(false);
                            }
                            answer.setVoted(true);
                            survey.setParticipated(true);
                            this.labyNetController.clearSurveyCache();
                            this.submitAction.press();
                        }
                    })));
                    surveyWidget.addChild(surveyButton);
                }
                break;
            }
            case TEXT: {
                final TextFieldWidget textField = new TextFieldWidget();
                textField.addId("survey-text-input");
                textField.maximalLength(250);
                final ButtonWidget submitButton = ButtonWidget.component(Component.translatable("labymod.ui.button.submit", new Component[0]));
                submitButton.setPressable(() -> {
                    if (textField.getText().isEmpty()) {
                        return;
                    }
                    else {
                        submitButton.setEnabled(false);
                        this.labyNetController.sendSurveyAnswer(survey, null, textField.getText(), result -> result.ifPresent(surveyAnswer -> {
                            if (surveyAnswer.isSuccess()) {
                                survey.setParticipated(true);
                                this.submitAction.press();
                            }
                        }));
                        return;
                    }
                });
                surveyWidget.addChild(textField);
                surveyWidget.addChild(submitButton);
                break;
            }
        }
        ((AbstractWidget<VerticalListWidget<Widget>>)this).addChild(surveyWidget);
    }
    
    private Survey getSurvey() {
        for (final Survey s : this.surveys) {
            if (!s.hasParticipated()) {
                return s;
            }
        }
        return null;
    }
}
