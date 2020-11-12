package it.unibas.mediapesataswing.modello;

import java.util.HashMap;
import java.util.Map;

public class Modello {
    
    private final Map<String, Object> mappaBean = new HashMap<String, Object>();
    
    public Object getBean(String nome) {
        return this.mappaBean.get(nome);
    }
    
    public void putBean(String nome, Object bean) {
        this.mappaBean.put(nome, bean);
    }

}
