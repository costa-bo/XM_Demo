package demo.services;

import demo.services.film.Film;

public class Rest {
    public RestValidations validate;
    public Film film = new Film();

    public Rest() {
        validate = new RestValidations(this);
    }

}
