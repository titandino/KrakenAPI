package kraken.plugin.api;

public class KrakenRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 5078457635731422050L;

	public KrakenRuntimeException() {
    }

    public KrakenRuntimeException(String message) {
        super(message);
    }

    public KrakenRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public KrakenRuntimeException(Throwable cause) {
        super(cause);
    }

    public KrakenRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
