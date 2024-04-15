package backend.enu;

import java.util.HashMap;
import java.util.Map;

public enum EnumFormaPagamento {

    A_VISTA("AVISTA"),
    PARCELADO_LOJA("PARCELADO LOJA"),
    PARCELADO_EMISSOR("PARCELADO EMISSOR");
    
    private final String codigo;

    
    private static final Map<String, EnumFormaPagamento> listaFormaPagamento = new HashMap<>();
    
    static {
    	for(EnumFormaPagamento formaPagamento: EnumFormaPagamento.values()) {
    		listaFormaPagamento.put(formaPagamento.getCodigo(), formaPagamento);
    	}
    }

    EnumFormaPagamento(String codigo) {
        this.codigo = codigo;
    }

    public String value() {
        return name();
    }

    public String getCodigo() {
        return codigo;
    }

    public static EnumFormaPagamento getFormaPagamento(String codigo) {
    	return listaFormaPagamento.get(codigo);
    }

    public static boolean isFormaPagamento(String codigo) {
    	return listaFormaPagamento.containsKey(codigo);
    }

}
