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

import fr.paris.lutece.plugins.grukeydiversification.business.EncryptionKey;
import fr.paris.lutece.plugins.grukeydiversification.business.EncryptionKeyHome;
import fr.paris.lutece.plugins.grukeydiversification.web.rs.dto.DecryptedIdentityDto;
import fr.paris.lutece.plugins.grukeydiversification.web.rs.dto.EncryptedIdentityDto;
import fr.paris.lutece.plugins.identitystore.business.IClientApplication;
import fr.paris.lutece.plugins.identitystore.service.encryption.IIdentityEncryptionService;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.IdentityDto;
import fr.paris.lutece.portal.service.util.AppException;

/**
 * This class encrypts / decrypts an {@link IdentityDto}.
 *
 */
public class IdentityEncryptionService implements IIdentityEncryptionService
{

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentityDto encrypt( IdentityDto identityDto, IClientApplication clientApplication )
    {
        EncryptionKey encryptionKey = fetchEncryptionKey( clientApplication );

        IdentityDto identityEncrypted = null;

        try
        {
            identityEncrypted = new EncryptedIdentityDto( identityDto, encryptionKey.getKey( ) );
        }
        catch( AppException e )
        {
            throw new AppException( "Error during encryption of identity. Used encryption key : code = " + encryptionKey.getCode( ), e );
        }

        return identityEncrypted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentityDto decrypt( IdentityDto identityDto, IClientApplication clientApplication )
    {
        EncryptionKey encryptionKey = fetchEncryptionKey( clientApplication );

        IdentityDto identityDecrypted = null;

        try
        {
            identityDecrypted = new DecryptedIdentityDto( identityDto, encryptionKey.getKey( ) );
        }
        catch( AppException e )
        {
            throw new AppException( "Error during decryption of identity. Used encryption key : code = " + encryptionKey.getCode( ), e );
        }

        return identityDecrypted;
    }

    /**
     * Fetches the encryption key from the specified client application
     * 
     * @param clientApplication
     *            the client application from which the encryption key is fetched
     * @return the found encryption key
     */
    private EncryptionKey fetchEncryptionKey( IClientApplication clientApplication )
    {
        if ( clientApplication == null )
        {
            throw new AppException( "The client application must not be null!" );
        }

        String strEncryptionKeyCode = clientApplication.getCode( );

        if ( strEncryptionKeyCode == null )
        {
            throw new AppException( "The client application code must not be null!" );
        }

        EncryptionKey encryptionKey = EncryptionKeyHome.findByCode( strEncryptionKeyCode );

        if ( encryptionKey == null )
        {
            throw new AppException( "Encryption key does not exists : " + strEncryptionKeyCode );
        }

        return encryptionKey;
    }

}
