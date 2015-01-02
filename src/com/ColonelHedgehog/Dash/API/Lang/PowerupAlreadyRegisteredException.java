package com.ColonelHedgehog.Dash.API.Lang;

/**
 * Created by ColonelHedgehog on 12/28/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class PowerupAlreadyRegisteredException extends Exception
{
    /**
     * The error message.
     */
    private String message;

    /**
     * This is the getter method for errorMessage.
     *
     * @return java.lang.String
     */
    public String getMessage()
    {
        return message;
    }

    public PowerupAlreadyRegisteredException(String message)
    {
        this.message = message;
    }
}
