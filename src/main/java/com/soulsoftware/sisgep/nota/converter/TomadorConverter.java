package com.soulsoftware.sisgep.nota.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.soulsoftware.sisgep.nota.model.Tomador;
import com.soulsoftware.sisgep.nota.repository.Tomadores;
import com.soulsoftware.sisgep.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Tomador.class)
public class TomadorConverter implements Converter {

	private Tomadores tomadores;

	public TomadorConverter() {
		tomadores = CDIServiceLocator.getBean(Tomadores.class);

	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Tomador retorno = null;
		
		if (value != null && !value.equals("")) {
			Long id = new Long(value);
			retorno = tomadores.porId(id);
			
			if(retorno == null){
				String descricaoErro = "Nenhum tomador não cadastrado.";
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, descricaoErro, descricaoErro);
				throw new ConverterException(message);
			}
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long id = ((Tomador) value).getId();
			return id == null ? "" : id.toString();
		}
		return "";
	}

}
