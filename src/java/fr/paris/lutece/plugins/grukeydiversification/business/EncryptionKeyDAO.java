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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for EncryptionKey objects
 */
public final class EncryptionKeyDAO implements IEncryptionKeyDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_encryption_key, code, key_value FROM grukeydiversification_encryptionkey WHERE id_encryption_key = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO grukeydiversification_encryptionkey ( code, key_value ) VALUES ( ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM grukeydiversification_encryptionkey WHERE id_encryption_key = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE grukeydiversification_encryptionkey SET id_encryption_key = ?, code = ?, key_value = ? WHERE id_encryption_key = ?";
    private static final String SQL_QUERY_SELECT_BY_CODE = "SELECT id_encryption_key, code, key_value FROM grukeydiversification_encryptionkey WHERE code = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_encryption_key, code, key_value FROM grukeydiversification_encryptionkey";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_encryption_key FROM grukeydiversification_encryptionkey";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( EncryptionKey encryptionKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, encryptionKey.getCode( ) );
            daoUtil.setString( nIndex++, encryptionKey.getKey( ) );

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                encryptionKey.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }
        finally
        {
            daoUtil.free( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EncryptionKey load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );
        EncryptionKey encryptionKey = null;

        if ( daoUtil.next( ) )
        {
            encryptionKey = dataToEncryptionKey( daoUtil );
        }

        daoUtil.free( );
        return encryptionKey;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( EncryptionKey encryptionKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, encryptionKey.getId( ) );
        daoUtil.setString( nIndex++, encryptionKey.getCode( ) );
        daoUtil.setString( nIndex++, encryptionKey.getKey( ) );
        daoUtil.setInt( nIndex, encryptionKey.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EncryptionKey selectByCode( String strCode, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_CODE, plugin );
        daoUtil.setString( 1, strCode );
        daoUtil.executeQuery( );
        EncryptionKey encryptionKey = null;

        if ( daoUtil.next( ) )
        {
            encryptionKey = dataToEncryptionKey( daoUtil );
        }

        daoUtil.free( );
        return encryptionKey;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<EncryptionKey> selectEncryptionKeysList( Plugin plugin )
    {
        List<EncryptionKey> encryptionKeyList = new ArrayList<EncryptionKey>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            EncryptionKey encryptionKey = dataToEncryptionKey( daoUtil );

            encryptionKeyList.add( encryptionKey );
        }

        daoUtil.free( );
        return encryptionKeyList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdEncryptionKeysList( Plugin plugin )
    {
        List<Integer> encryptionKeyList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            encryptionKeyList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return encryptionKeyList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectEncryptionKeysReferenceList( Plugin plugin )
    {
        ReferenceList encryptionKeyList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            encryptionKeyList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return encryptionKeyList;
    }

    /**
     * Converts data from the specified {@code DAOUtil} object to a {@code EncryptionKey} object
     * 
     * @param daoUtil
     *            the {@code DAOUtil} object
     * @return the {@code EncryptionKey} object
     */
    private static EncryptionKey dataToEncryptionKey( DAOUtil daoUtil )
    {
        EncryptionKey encryptionKey = new EncryptionKey( );
        int nIndex = 1;

        encryptionKey.setId( daoUtil.getInt( nIndex++ ) );
        encryptionKey.setCode( daoUtil.getString( nIndex++ ) );
        encryptionKey.setKey( daoUtil.getString( nIndex++ ) );

        return encryptionKey;
    }
}
