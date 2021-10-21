package br.helbertrios.guice.app.bean;

import com.google.inject.Inject;

public class TextEditor {

    private final SpellChecker spellChecker;

    @Inject
    public TextEditor(SpellChecker spellChecker) {
        this.spellChecker = spellChecker;
    }

    public String makeSpellCheck() {
        return spellChecker.checkSpelling();
    }

}
