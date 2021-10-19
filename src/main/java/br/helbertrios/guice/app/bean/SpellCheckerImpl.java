package br.helbertrios.guice.app.bean;

public class SpellCheckerImpl implements SpellChecker {
    @Override
    public String checkSpelling() {
        return "Inside checkSpelling.";
    }
}