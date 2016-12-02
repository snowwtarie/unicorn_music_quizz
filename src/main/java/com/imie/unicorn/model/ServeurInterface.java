package com.imie.unicorn.model;

import java.io.IOException;


public interface ServeurInterface {

    void envoyerMessage(ThreadServeur origine, String msg) throws IOException;
}
