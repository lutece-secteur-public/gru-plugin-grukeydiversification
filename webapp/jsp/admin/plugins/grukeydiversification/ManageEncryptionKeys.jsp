<jsp:useBean id="manageencryptionkeyEncryptionKey" scope="session" class="fr.paris.lutece.plugins.grukeydiversification.web.EncryptionKeyJspBean" />
<% String strContent = manageencryptionkeyEncryptionKey.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
