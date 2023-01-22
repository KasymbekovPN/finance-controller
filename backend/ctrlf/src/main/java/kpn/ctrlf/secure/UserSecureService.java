package kpn.ctrlf.secure;

public interface UserSecureService<U> {
	boolean checkCredential(U user);
}
