package kpn.ctrlf.client.i18n.params;

import kpn.ctrlf.client.i18n.params.I18nParams;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class I18nParamsImpl implements I18nParams<Result<Map<String, String>>> {
	private static final String CODE_BASE = "i18n-params.checking-fail";
	private static final String CODE_BN = ".bn";
	private static final String CODE_EXT = ".ext";
	private static final String CODE_LS = ".ls";

	@Setter
	private String baseName;
	@Setter
	private String extension;
	@Setter
	private List<String> locales;

	public Result<Map<String, String>> calculate() {

		String code = "";
		if (baseName == null){
			code = CODE_BASE + CODE_BN;
		}
		if (extension == null){
			code = code.isEmpty()
				? CODE_BASE + CODE_EXT
				: code + CODE_EXT;
		}
		if (locales == null || locales.isEmpty()){
			code = code.isEmpty()
				? CODE_BASE + CODE_LS
				: code + CODE_LS;
		}

		if (code.isEmpty()){
			String ext = extension.isEmpty() ? "" : "." + extension;
			HashMap<String, String> paths = new HashMap<>();
			for (String locale : locales) {
				paths.put(locale, baseName + locale + ext);
			}

			return ImmutableResult.<Map<String, String>>ok(paths);
		}
		return ImmutableResult.<Map<String, String>>fail(code);
	}
}
