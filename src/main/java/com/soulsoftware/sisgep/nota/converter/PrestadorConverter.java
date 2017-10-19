package com.soulsoftware.sisgep.nota.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.soulsoftware.sisgep.nota.model.Prestador;
import com.soulsoftware.sisgep.nota.repository.Prestadores;
import com.soulsoftware.sisgep.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Prestador.class)
public class PrestadorConverter implements Converter {

	// @Inject
	private Prestadores prestadores;

	public PrestadorConverter() {
		prestadores = CDIServiceLocator.getBean(Prestadores.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Prestador retorno = null;

		if (value != null) {
			Long id = new Long(value);
			retorno = prestadores.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Prestador prestador = (Prestador) value;

			return prestador.getId() == null ? null : prestador.getId().toString();
		}

		return "";
	}

}
