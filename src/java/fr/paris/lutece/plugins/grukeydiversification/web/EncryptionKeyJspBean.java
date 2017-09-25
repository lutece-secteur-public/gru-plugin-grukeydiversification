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

package fr.paris.lutece.plugins.grukeydiversification.web;

import fr.paris.lutece.plugins.grukeydiversification.business.EncryptionKey;
import fr.paris.lutece.plugins.grukeydiversification.business.EncryptionKeyHome;
import fr.paris.lutece.plugins.grukeydiversification.business.exception.EncryptionKeyAlreadyExistException;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage EncryptionKey features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageEncryptionKeys.jsp", controllerPath = "jsp/admin/plugins/grukeydiversification/", right = "GRUKEYDIVERSIFICATION_MANAGEMENT" )
public class EncryptionKeyJspBean extends AbstractManageEncryptionKeyJspBean
{
    /**
     * Generated serial ID
     */
    private static final long serialVersionUID = 3101755353106198571L;

    // Templates
    private static final String TEMPLATE_MANAGE_ENCRYPTIONKEYS = "/admin/plugins/grukeydiversification/manage_encryptionkeys.html";
    private static final String TEMPLATE_CREATE_ENCRYPTIONKEY = "/admin/plugins/grukeydiversification/create_encryptionkey.html";
    private static final String TEMPLATE_MODIFY_ENCRYPTIONKEY = "/admin/plugins/grukeydiversification/modify_encryptionkey.html";

    // Parameters
    private static final String PARAMETER_ID_ENCRYPTIONKEY = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_ENCRYPTIONKEYS = "grukeydiversification.manage_encryptionkeys.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_ENCRYPTIONKEY = "grukeydiversification.modify_encryptionkey.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_ENCRYPTIONKEY = "grukeydiversification.create_encryptionkey.pageTitle";

    // Markers
    private static final String MARK_ENCRYPTIONKEY_LIST = "encryptionkey_list";
    private static final String MARK_ENCRYPTIONKEY = "encryptionkey";

    private static final String JSP_MANAGE_ENCRYPTIONKEYS = "jsp/admin/plugins/grukeydiversification/ManageEncryptionKeys.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_ENCRYPTIONKEY = "grukeydiversification.message.confirmRemoveEncryptionKey";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "grukeydiversification.model.entity.encryptionkey.attribute.";

    // Views
    private static final String VIEW_MANAGE_ENCRYPTIONKEYS = "manageEncryptionKeys";
    private static final String VIEW_CREATE_ENCRYPTIONKEY = "createEncryptionKey";
    private static final String VIEW_MODIFY_ENCRYPTIONKEY = "modifyEncryptionKey";

    // Actions
    private static final String ACTION_CREATE_ENCRYPTIONKEY = "createEncryptionKey";
    private static final String ACTION_MODIFY_ENCRYPTIONKEY = "modifyEncryptionKey";
    private static final String ACTION_REMOVE_ENCRYPTIONKEY = "removeEncryptionKey";
    private static final String ACTION_CONFIRM_REMOVE_ENCRYPTIONKEY = "confirmRemoveEncryptionKey";

    // Infos
    private static final String INFO_ENCRYPTIONKEY_CREATED = "grukeydiversification.info.encryptionkey.created";
    private static final String INFO_ENCRYPTIONKEY_UPDATED = "grukeydiversification.info.encryptionkey.updated";
    private static final String INFO_ENCRYPTIONKEY_REMOVED = "grukeydiversification.info.encryptionkey.removed";

    // Errors
    private static final String ERROR_ENCRYPTIONKEY_ALREADY_EXISTS = "grukeydiversification.error.encryptionkey.alreadyExists";

    // Session variable to store working values
    private EncryptionKey _encryptionkey;

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_ENCRYPTIONKEYS, defaultView = true )
    public String getManageEncryptionKeys( HttpServletRequest request )
    {
        _encryptionkey = null;
        List<EncryptionKey> listEncryptionKeys = EncryptionKeyHome.getEncryptionKeysList( );
        Map<String, Object> model = getPaginatedListModel( request, MARK_ENCRYPTIONKEY_LIST, listEncryptionKeys, JSP_MANAGE_ENCRYPTIONKEYS );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_ENCRYPTIONKEYS, TEMPLATE_MANAGE_ENCRYPTIONKEYS, model );
    }

    /**
     * Returns the form to create a encryptionkey
     *
     * @param request
     *            The Http request
     * @return the html code of the encryptionkey form
     */
    @View( VIEW_CREATE_ENCRYPTIONKEY )
    public String getCreateEncryptionKey( HttpServletRequest request )
    {
        _encryptionkey = ( _encryptionkey != null ) ? _encryptionkey : new EncryptionKey( );

        Map<String, Object> model = getModel( );
        model.put( MARK_ENCRYPTIONKEY, _encryptionkey );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_ENCRYPTIONKEY, TEMPLATE_CREATE_ENCRYPTIONKEY, model );
    }

    /**
     * Process the data capture form of a new encryptionkey
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_ENCRYPTIONKEY )
    public String doCreateEncryptionKey( HttpServletRequest request )
    {
        populate( _encryptionkey, request );

        // Check constraints
        if ( !validateBean( _encryptionkey, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_ENCRYPTIONKEY );
        }

        try
        {
            EncryptionKeyHome.create( _encryptionkey );
        }
        catch( EncryptionKeyAlreadyExistException e )
        {
            addError( ERROR_ENCRYPTIONKEY_ALREADY_EXISTS, getLocale( ) );
            return redirectView( request, VIEW_CREATE_ENCRYPTIONKEY );
        }

        addInfo( INFO_ENCRYPTIONKEY_CREATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_ENCRYPTIONKEYS );
    }

    /**
     * Manages the removal form of a encryptionkey whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_ENCRYPTIONKEY )
    public String getConfirmRemoveEncryptionKey( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ENCRYPTIONKEY ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_ENCRYPTIONKEY ) );
        url.addParameter( PARAMETER_ID_ENCRYPTIONKEY, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_ENCRYPTIONKEY, url.getUrl( ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a encryptionkey
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage encryptionkeys
     */
    @Action( ACTION_REMOVE_ENCRYPTIONKEY )
    public String doRemoveEncryptionKey( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ENCRYPTIONKEY ) );
        EncryptionKeyHome.remove( nId );
        addInfo( INFO_ENCRYPTIONKEY_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_ENCRYPTIONKEYS );
    }

    /**
     * Returns the form to update info about a encryptionkey
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_ENCRYPTIONKEY )
    public String getModifyEncryptionKey( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ENCRYPTIONKEY ) );

        if ( _encryptionkey == null || ( _encryptionkey.getId( ) != nId ) )
        {
            _encryptionkey = EncryptionKeyHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_ENCRYPTIONKEY, _encryptionkey );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_ENCRYPTIONKEY, TEMPLATE_MODIFY_ENCRYPTIONKEY, model );
    }

    /**
     * Process the change form of a encryptionkey
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_ENCRYPTIONKEY )
    public String doModifyEncryptionKey( HttpServletRequest request )
    {
        populate( _encryptionkey, request );

        // Check constraints
        if ( !validateBean( _encryptionkey, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_ENCRYPTIONKEY, PARAMETER_ID_ENCRYPTIONKEY, _encryptionkey.getId( ) );
        }

        try
        {
            EncryptionKeyHome.update( _encryptionkey );
        }
        catch( EncryptionKeyAlreadyExistException e )
        {
            addError( ERROR_ENCRYPTIONKEY_ALREADY_EXISTS, getLocale( ) );
            return redirect( request, VIEW_MODIFY_ENCRYPTIONKEY, PARAMETER_ID_ENCRYPTIONKEY, _encryptionkey.getId( ) );
        }

        addInfo( INFO_ENCRYPTIONKEY_UPDATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_ENCRYPTIONKEYS );
    }
}
