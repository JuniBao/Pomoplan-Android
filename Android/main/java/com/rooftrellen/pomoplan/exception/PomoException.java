package com.rooftrellen.pomoplan.exception;

/**
 * PomoException is an Exception for describing and handling custom exceptions.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class PomoException extends Exception {

    /**
     * Error code.
     *
     * @since 1.0.0
     */
    private ErrorCode errorCode;

    /**
     * Error message.
     *
     * @since 1.0.0
     */
    private String message;

    /**
     * Initializes with number and message.
     *
     * @param errorCode error code.
     * @param message error message.
     * @since 1.0.0
     */
    public PomoException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * Gets error tag.
     *
     * @return error tag.
     * @since 1.0.0
     */
    public String getErrorTag() {
        return errorCode.getTag();
    }

    /**
     * Gets the String value.
     *
     * @return the String value of PomoException.
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return errorCode.getNumber() + "-"
                + errorCode.getTag() + ":"
                + message;
    }

    /**
     * ErrorCode is an enum class for setting every error code and its tag.
     *
     * @since 1.0.0
     */
    public enum ErrorCode {

        /**
         * Error code for different exception.
         *
         * @since 1.0.0
         */
        NULL_OBJECT(101),
        NEGATIVE_VALUE(102),
        INVALID_DB(103);

        /**
         * Error code's tag.
         *
         * @since 1.0.0
         */
        private final String tag;

        /**
         * Error code's number.
         *
         * @since 1.0.0
         */
        private final int number;

        /**
         * Initializes an error code's tag.
         *
         * @param number corresponding error code's number.
         * @since 1.0.0
         */
        ErrorCode(int number) {
            this.number = number;
            switch (number) {
                case 101:
                case 102:
                    this.tag = "IllegalArgumentException";
                    break;
                case 103:
                    this.tag = "DatabaseException";
                    break;
                default:
                    this.tag = "UnknownException";
                    break;
            }
        }

        /**
         * Gets the number of ErrorCode.
         *
         * @return the number of ErrorCode.
         * @since 1.0.0
         */
        public int getNumber() {
            return number;
        }

        /**
         * Gets the tag of ErrorCode.
         *
         * @return the tag of ErrorCode.
         * @since 1.0.0
         */
        public String getTag() {
            return tag;
        }

    }

}
