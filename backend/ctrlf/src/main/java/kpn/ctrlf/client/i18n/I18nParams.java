package kpn.ctrlf.client.i18n;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public final class I18nParams {
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

	public Result<List<String>> calculatePaths() {

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
			ArrayList<String> paths = new ArrayList<>();
			for (String locale : locales) {
				paths.add(baseName + locale + ext);
			}

			return ImmutableResult.<List<String>>ok(paths);
		}
		return ImmutableResult.<List<String>>fail(code);
	}
}
