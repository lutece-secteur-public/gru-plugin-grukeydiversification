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
package fr.paris.lutece.plugins.grukeydiversification.business.customer;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.grubusiness.business.customer.Customer;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.util.keydiversification.DiversificationService;
import fr.paris.lutece.util.keydiversification.KeyDiversificationException;

/**
 * <p>
 * Decorator of a {@link Customer} in order to decrypt attributes
 * </p>
 * <p>
 * The encrypted attributes are :
 * <ul>
 * <li>the id</li>
 * </ul>
 * </p>
 *
 */
public class DecryptedCustomer extends Customer
{
    /**
     * Generated serial ID
     */
    private static final long serialVersionUID = 961821157525763544L;

    private final Customer _customer;
    private String _strCustomerId;

    /**
     * Constructor
     * 
     * @param customer
     *            the {@link Customer} to decrypt
     * @param strEncryptionKey
     *            the encryption key used to encrypt the {@code Customer}
     * @throws AppException
     *             if there is an error during the decryption
     */
    public DecryptedCustomer( Customer customer, String strEncryptionKey )
    {
        super( );
        _customer = customer;

        if ( StringUtils.isBlank( strEncryptionKey ) )
        {
            throw new AppException( "Encrytion on customer is enabled but the encryption key is blank!" );
        }

        if ( _customer.getId( ) != null )
        {
            try
            {
                _strCustomerId = DiversificationService.getIDPKey( _customer.getId( ), strEncryptionKey );
            }
            catch( KeyDiversificationException e )
            {
                StringBuilder stringBuilder = new StringBuilder( "Error during decryption of Customer : connectionId = " );
                stringBuilder.append( _customer.getConnectionId( ) ).append( ", customerId = " ).append( _customer.getId( ) );
                throw new AppException( stringBuilder.toString( ), e );
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getId( )
    {
        return _strCustomerId;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setId( String strId )
    {
        _strCustomerId = strId;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getIdTitle( )
    {
        return _customer.getIdTitle( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setIdTitle( int nIdTitle )
    {
        _customer.setIdTitle( nIdTitle );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getFirstname( )
    {
        return _customer.getFirstname( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setFirstname( String strFirstname )
    {
        _customer.setFirstname( strFirstname );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getLastname( )
    {
        return _customer.getLastname( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setLastname( String strLastname )
    {
        _customer.setLastname( strLastname );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getFamilyname( )
    {
        return _customer.getFamilyname( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setFamilyname( String strFamilyname )
    {
        _customer.setFamilyname( strFamilyname );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean getHasAccount( )
    {
        return _customer.getHasAccount( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setHasAccount( boolean bHasAccount )
    {
        _customer.setHasAccount( bHasAccount );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getAccountLogin( )
    {
        return _customer.getAccountLogin( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setAccountLogin( String strAccountLogin )
    {
        _customer.setAccountLogin( strAccountLogin );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getConnectionId( )
    {
        return _customer.getConnectionId( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setConnectionId( String strConnectionId )
    {
        _customer.setConnectionId( strConnectionId );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getEmail( )
    {
        return _customer.getEmail( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setEmail( String strEmail )
    {
        _customer.setEmail( strEmail );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean getIsEmailVerified( )
    {
        return _customer.getIsEmailVerified( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setIsEmailVerified( boolean bIsEmailVerified )
    {
        _customer.setIsEmailVerified( bIsEmailVerified );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getMobilePhone( )
    {
        return _customer.getMobilePhone( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setMobilePhone( String strMobilePhone )
    {
        _customer.setMobilePhone( strMobilePhone );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getFixedPhoneNumber( )
    {
        return _customer.getFixedPhoneNumber( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setFixedPhoneNumber( String strFixedPhoneNumber )
    {
        _customer.setFixedPhoneNumber( strFixedPhoneNumber );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean getIsMobilePhoneVerified( )
    {
        return _customer.getIsMobilePhoneVerified( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setIsMobilePhoneVerified( boolean bIsMobilePhoneVerified )
    {
        _customer.setIsMobilePhoneVerified( bIsMobilePhoneVerified );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getExtrasAttributes( )
    {
        return _customer.getExtrasAttributes( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setExtrasAttributes( String strExtrasAttributes )
    {
        _customer.setExtrasAttributes( strExtrasAttributes );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getBirthDate( )
    {
        return _customer.getBirthDate( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setBirthDate( String strBirthDate )
    {
        _customer.setBirthDate( strBirthDate );
    }

}
