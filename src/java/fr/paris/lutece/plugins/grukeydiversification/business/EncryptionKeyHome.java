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
package fr.paris.lutece.plugins.grukeydiversification.business;

import fr.paris.lutece.plugins.grukeydiversification.business.exception.EncryptionKeyAlreadyExistException;
import fr.paris.lutece.plugins.grukeydiversification.service.cache.EncryptionKeyCacheService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for EncryptionKey objects
 */
public final class EncryptionKeyHome
{
    // Static variable pointed at the DAO instance
    private static IEncryptionKeyDAO _dao = SpringContextService.getBean( "grukeydiversification.encryptionKeyDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "grukeydiversification" );
    private static EncryptionKeyCacheService _cache = EncryptionKeyCacheService.getInstance( );

    /**
     * Private constructor - this class need not be instantiated
     */
    private EncryptionKeyHome( )
    {
    }

    /**
     * Create an instance of the encryptionKey class
     * 
     * @param encryptionKey
     *            The instance of the EncryptionKey which contains the informations to store
     * @return The instance of encryptionKey which has been created with its primary key.
     * @throws EncryptionKeyAlreadyExistException
     *             if an encryption key already exists with the same code
     */
    public static EncryptionKey create( EncryptionKey encryptionKey ) throws EncryptionKeyAlreadyExistException
    {
        EncryptionKey encryptionKeyInDatabase = findByCode( encryptionKey.getCode( ) );

        if ( encryptionKeyInDatabase != null )
        {
            throw new EncryptionKeyAlreadyExistException( "An encryption key already exists for the code : " + encryptionKey.getCode( ) );
        }

        _dao.insert( encryptionKey, _plugin );

        return encryptionKey;
    }

    /**
     * Update of the encryptionKey which is specified in parameter
     * 
     * @param encryptionKey
     *            The instance of the EncryptionKey which contains the data to store
     * @return The instance of the encryptionKey which has been updated
     * @throws EncryptionKeyAlreadyExistException
     *             if an encryption key already exists with the same code
     */
    public static EncryptionKey update( EncryptionKey encryptionKey ) throws EncryptionKeyAlreadyExistException
    {
        EncryptionKey encryptionKeyInDatabase = findByCode( encryptionKey.getCode( ) );

        if ( encryptionKeyInDatabase != null && encryptionKeyInDatabase.getId( ) != encryptionKey.getId( ) )
        {
            throw new EncryptionKeyAlreadyExistException( "An encryption key already exists for the code : " + encryptionKey.getCode( ) );
        }

        encryptionKeyInDatabase = findByPrimaryKey( encryptionKey.getId( ) );
        _cache.remove( encryptionKeyInDatabase );

        _dao.store( encryptionKey, _plugin );

        return encryptionKey;
    }

    /**
     * Remove the encryptionKey whose identifier is specified in parameter
     * 
     * @param nKey
     *            The encryptionKey Id
     */
    public static void remove( int nKey )
    {
        EncryptionKey encryptionKey = findByPrimaryKey( nKey );

        if ( encryptionKey != null )
        {
            _dao.delete( nKey, _plugin );

            _cache.remove( encryptionKey );
        }
    }

    /**
     * Returns an instance of a encryptionKey whose identifier is specified in parameter
     * 
     * @param nKey
     *            The encryptionKey primary key
     * @return an instance of EncryptionKey
     */
    public static EncryptionKey findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Returns a instance of a {@code EncryptionKey} whose code is specified in parameter
     * 
     * @param strCode
     *            the code
     * @return an instance of {@code EncryptionKey}
     */
    public static EncryptionKey findByCode( String strCode )
    {
        String strCacheKey = _cache.getCacheKey( strCode );
        EncryptionKey encryptionKey = (EncryptionKey) _cache.getFromCache( strCacheKey );

        if ( encryptionKey == null )
        {
            encryptionKey = _dao.selectByCode( strCode, _plugin );

            if ( encryptionKey != null )
            {
                _cache.putInCache( strCacheKey, encryptionKey );
            }
        }

        return encryptionKey;
    }

    /**
     * Load the data of all the encryptionKey objects and returns them as a list
     * 
     * @return the list which contains the data of all the encryptionKey objects
     */
    public static List<EncryptionKey> getEncryptionKeysList( )
    {
        return _dao.selectEncryptionKeysList( _plugin );
    }

    /**
     * Load the id of all the encryptionKey objects and returns them as a list
     * 
     * @return the list which contains the id of all the encryptionKey objects
     */
    public static List<Integer> getIdEncryptionKeysList( )
    {
        return _dao.selectIdEncryptionKeysList( _plugin );
    }

    /**
     * Load the data of all the encryptionKey objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the encryptionKey objects
     */
    public static ReferenceList getEncryptionKeysReferenceList( )
    {
        return _dao.selectEncryptionKeysReferenceList( _plugin );
    }
}
