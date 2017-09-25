/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.grukeydiversification.service.encryption;

import fr.paris.lutece.plugins.grubusiness.business.customer.Customer;
import fr.paris.lutece.plugins.grubusiness.business.demand.Demand;
import fr.paris.lutece.plugins.grubusiness.service.encryption.ICustomerEncryptionService;
import fr.paris.lutece.plugins.grukeydiversification.business.EncryptionKey;
import fr.paris.lutece.plugins.grukeydiversification.business.EncryptionKeyHome;
import fr.paris.lutece.plugins.grukeydiversification.business.customer.DecryptedCustomer;
import fr.paris.lutece.plugins.grukeydiversification.business.customer.EncryptedCustomer;
import fr.paris.lutece.portal.service.util.AppException;

/**
 * This class encrypts / decrypts an {@link Customer}.
 *
 */
public class CustomerEncryptionService implements ICustomerEncryptionService
{

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer encrypt( Customer customer, Demand demand )
    {
        if ( demand == null )
        {
            throw new AppException( "The demand must not be null!" );
        }

        return encrypt( customer, demand.getTypeId( ) );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Customer encrypt( Customer customer, String strCode )
    {
        EncryptionKey encryptionKey = fetchEncryptionKey( strCode );

        Customer customerEncrypted = null;

        try
        {
            customerEncrypted = new EncryptedCustomer( customer, encryptionKey.getKey( ) );
        }
        catch( AppException e )
        {
            throw new AppException( "Error during encryption of customer. Used encryption key : code = " + encryptionKey.getCode( ), e );
        }

        return customerEncrypted;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Customer decrypt( Customer customer, Demand demand )
    {
        if ( demand == null )
        {
            throw new AppException( "The demand must not be null!" );
        }

        return decrypt( customer, demand.getTypeId( ) );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Customer decrypt( Customer customer, String strCode )
    {
        EncryptionKey encryptionKey = fetchEncryptionKey( strCode );

        Customer customerDecrypted = null;

        try
        {
            customerDecrypted = new DecryptedCustomer( customer, encryptionKey.getKey( ) );
        }
        catch( AppException e )
        {
            throw new AppException( "Error during decryption of customer. Used encryption key : code = " + encryptionKey.getCode( ), e );
        }

        return customerDecrypted;
    }
    
    /**
     * Fetches the encryption key from the specified code
     * 
     * @param strEncryptionKeyCode
     *            the encryption key code
     * @return the found encryption key
     */
    private EncryptionKey fetchEncryptionKey( String strEncryptionKeyCode )
    {
        if ( strEncryptionKeyCode == null )
        {
            throw new AppException( "The code must not be null!" );
        }
        
        EncryptionKey encryptionKey = EncryptionKeyHome.findByCode( strEncryptionKeyCode );

        if ( encryptionKey == null )
        {
            throw new AppException( "Encryption key does not exists : " + strEncryptionKeyCode );
        }

        return encryptionKey;
    }

}
