 import java.io.Serializable;

import javax.faces.bean.ManagedBean;
@ManagedBean
public class TipoPessoaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String tipoPessoa="F";

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

}
