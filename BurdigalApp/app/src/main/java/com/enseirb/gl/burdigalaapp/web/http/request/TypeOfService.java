package com.enseirb.gl.burdigalaapp.web.http.request;

import java.util.NoSuchElementException;

/**
 * Created by rchabot on 23/11/15.
 */
public enum TypeOfService {
    SIGSANITAIRE, PARCJARDIN, SIGPARKPUB, SIGSTAVELO;

    @Override
    public String toString() {
        switch (this) {
            case SIGSANITAIRE:
                return "sigsanitaire";
            case PARCJARDIN:
                return "parcjardin";
            case SIGPARKPUB:
                return "sigparkpub";
            case SIGSTAVELO:
                return "sigstavelo";
            default:
                return ""; // TODO throw exception
        }
    }
}
