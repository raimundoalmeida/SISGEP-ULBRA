package com.soulsoftware.sisgep.nota.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.soulsoftware.sisgep.nota.model.LancamentoTributo;
import com.soulsoftware.sisgep.nota.repository.LancamentosTributos;
import com.soulsoftware.sisgep.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = LancamentoTributo.class)
public class LancamentoConverte implements Converter {

	private LancamentosTributos lancamentos;

	public LancamentoConverte() {
		lancamentos = CDIServiceLocator.getBean(LancamentosTributos.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		LancamentoTributo retorno = null;

		if (value != null ) {
			Long id = new Long(value);
			retorno = lancamentos.porId(id);

			}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			LancamentoTributo lancamento = (LancamentoTributo) value;
			return lancamento.getId() == null ? null : lancamento.getId().toString();
		}

		return "";
	}

}
