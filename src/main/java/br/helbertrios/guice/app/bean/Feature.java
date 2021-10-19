package br.helbertrios.guice.app.bean;


import br.helbertrios.guice.app.dao.DAO;
import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class Feature {

    @Inject
    private DAO dao;

    public String getMessage() {
        return dao.getMessage();
    }

}
