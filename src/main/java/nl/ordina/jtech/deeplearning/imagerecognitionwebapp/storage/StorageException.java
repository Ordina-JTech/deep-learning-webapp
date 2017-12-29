package nl.ordina.jtech.deeplearning.imagerecognitionwebapp.storage;

class StorageException extends RuntimeException {

    StorageException(String message) {
        super(message);
    }

    StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
