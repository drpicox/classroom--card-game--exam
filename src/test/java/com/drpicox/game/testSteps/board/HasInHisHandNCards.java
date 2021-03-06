package com.drpicox.game.testSteps.board;

import com.drpicox.game.forms.VisibleGameForm;
import com.drpicox.game.testPost.TestPostForms;
import com.drpicox.game.testPost.reader.PostLine;
import com.drpicox.game.testSteps.AbstractPostLineStep;
import org.springframework.stereotype.Component;

import static com.google.common.truth.Truth.assertThat;

@Component
public class HasInHisHandNCards extends AbstractPostLineStep {

    private final TestPostForms testPostForms;

    public HasInHisHandNCards(TestPostForms testPostForms) {
        this.testPostForms = testPostForms;
    }

    @Override
    protected String getRegex() {
        return "_([^_]+)_ has in h[ei][rs] hand _([^_]+)_ _([^_]+)_ card(?!s? of)";
    }

    @Override
    protected void run(PostLine line, String[] match) {
        var player = match[1];
        var square = 0;
        var expectedCount = Integer.parseInt(match[2]);
        var type = match[3];

        if (expectedCount == 0)
            throw new AssertionError("Do not use this sentence, use a sentence with no instead of _0_");

        var response = testPostForms.getForm(VisibleGameForm.class);
        var cards = response.getCards().ofOwner(player).atSquare(square).ofType(type);

        assertThat(cards).hasSize(expectedCount);
    }
}
