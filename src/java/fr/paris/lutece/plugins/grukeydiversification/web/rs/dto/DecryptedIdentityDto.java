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
package fr.paris.lutece.plugins.grukeydiversification.web.rs.dto;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.identitystore.web.rs.dto.AttributeDto;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.IdentityDto;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.util.keydiversification.DiversificationService;
import fr.paris.lutece.util.keydiversification.KeyDiversificationException;

/**
 * <p>
 * Decorator of a {@link IdentityDto} in order to decrypt attributes
 * </p>
 * <p>
 * The encrypted attributes are :
 * <ul>
 * <li>the customer id</li>
 * </ul>
 * </p>
 *
 */
public class DecryptedIdentityDto extends IdentityDto
{

    /**
     * Generated serial ID
     */
    private static final long serialVersionUID = -8236870738465724413L;

    private final IdentityDto _identityDto;
    private String _strCustomerId;

    /**
     * Constructor
     * 
     * @param identityDto
     *            the {@link IdentityDto} to decrypt
     * @param strEncryptionKey
     *            the encryption key used to encrypt the {@code IdentityDto}
     * @throws AppException
     *             if there is an error during the decryption
     */
    public DecryptedIdentityDto( IdentityDto identityDto, String strEncryptionKey )
    {
        super( );
        _identityDto = identityDto;

        if ( StringUtils.isBlank( strEncryptionKey ) )
        {
            throw new AppException( "Encrytion on Identity is enabled but the encryption key is blank!" );
        }

        if ( _identityDto.getCustomerId( ) != null )
        {
            try
            {
                _strCustomerId = DiversificationService.getIDPKey( _identityDto.getCustomerId( ), strEncryptionKey );
            }
            catch( KeyDiversificationException e )
            {
                StringBuilder stringBuilder = new StringBuilder( "Error during decryption of Identity : connectionId = " );
                stringBuilder.append( _identityDto.getConnectionId( ) ).append( ", customerId = " ).append( _identityDto.getCustomerId( ) );
                throw new AppException( stringBuilder.toString( ), e );
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Map<String, AttributeDto> getAttributes( )
    {
        return _identityDto.getAttributes( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setAttributes( Map<String, AttributeDto> mapAttributes )
    {
        _identityDto.setAttributes( mapAttributes );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getConnectionId( )
    {
        return _identityDto.getConnectionId( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setConnectionId( String connectionId )
    {
        _identityDto.setConnectionId( connectionId );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getCustomerId( )
    {
        return _strCustomerId;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setCustomerId( String strCustomerId )
    {
        _strCustomerId = strCustomerId;
    }

}
