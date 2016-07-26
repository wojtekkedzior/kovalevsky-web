/*
 * CorruptHeaderException.java
 *
 * Created on 28 August 2006, 22:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kovalevsky.imaging;


/**
 *
 * @author wojtek
 */
public class CorruptHeaderException extends Exception
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 413000425157379156L;
	/** Creates a new instance of CorruptHeaderException */
    public CorruptHeaderException() 
    {
        
    }
    
    public CorruptHeaderException(String description)
    {
        super(description);
    }
    
//------Public------------------------------------------------------------------    
    public String toString()
    {
        return "A component of the header is not valid.";
    }

    public String getMessage() 
    {
        return "A component of the header is not valid.";
    }
//------protected---------------------------------------------------------------    
    protected void finalize() throws Throwable 
    {
        
    }
//------Variables---------------------------------------------------------------      
    String description;
}
