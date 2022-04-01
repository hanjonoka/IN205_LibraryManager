<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Library Management</title>
  <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="assets/css/custom.css" rel="stylesheet" type="text/css" />
</head>

<body>
  <jsp:include page='menu.jsp'></jsp:include>
  <main>
    <section class="content">
      <div class="page-announce valign-wrapper">
        <a href="#" data-activates="slide-out" class="button-collapse valign hide-on-large-only"><i class="material-icons">menu</i></a>
        <h1 class="page-announce-text valign">Fiche livre</h1>
      </div>
      <div class="row">
      <div class="container">
      <h5>D�tails du livre n�<c:out value="${livre.getId()}"/></h5>
        <div class="row">
	      <form action="/TP3Ensta/livre_details?id=<c:out value="${livre.getId()}"/>" method="post" class="col s12"> <!-- TODO : remplacer idDuLivre par l'id du livre -->
	        <div class="row">
	          <div class="input-field col s12">
	            <input id="titre" type="text" value="<c:out value="${livre.getTitre()}"/>" name="titre"> <!-- TODO : remplacer titreDuLivre par le titre du livre -->
	            <label for="titre">Titre</label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="input-field col s6">
	            <input id="auteur" type="text" value="<c:out value="${livre.getAuteur()}"/>" name="auteur"> <!-- TODO : remplacer auteurDuLivre par l'auteur du livre -->
	            <label for="auteur">Auteur</label>
	          </div>
	          <div class="input-field col s6">
	            <input id="isbn" type="text" value="<c:out value="${livre.getIsbn()}"/>" name="isbn"> <!-- TODO : remplacer isbnDuLivre par l'isbn du livre -->
	            <label for="isbn">ISBN</label>
	          </div>
	        </div>
	        <div class="row center">
	          <button class="btn waves-effect waves-light" type="submit">Enregistrer</button>
	          <button class="btn waves-effect waves-light orange" type="reset">Annuler</button>
	        </div>
	      </form>
	      
	      <form action="/TP3Ensta/livre_delete" method="get" class="col s12">
	        <input type="hidden" value="<c:out value="${livre.getId()}"/>" name="id"> <!-- TODO : remplacer idDuLivre par l'id du livre -->
	        <div class="row center">
	          <button class="btn waves-effect waves-light red" type="submit">Supprimer le livre
	            <i class="material-icons right">delete</i>
	          </button>
	        </div>
	      </form>
	    </div>
	    <c:out value="${ debug }"/>
	    <c:if test="${ emprunt != null }">
	        <div class="row">
	          <div class="col s12">
		        <table class="striped">
	              <thead>
	                <tr>
	                  <th>Emprunteur</th>
	                  <th>Date d'emprunt</th>
	                  <th>Retour</th>
	                </tr>
	              </thead>
	              <tbody id="results">
	
	                <tr>
	                  <td><c:out value="${ emprunt.getMembre().getPrenom() }"/> <c:out value="${ emprunt.getMembre().getNom() }"/></td>
	                  <td><c:out value="${ emprunt.getDateEmprunt() }"/></td>
	                  <td>
	                    <a href="emprunt_return?id=<c:out value="${ emprunt.getId() }" />">RETOUR<ion-icon class="table-item" name="log-in"></a>
	                  </td>
	                </tr>
	
					<!-- TODO : parcourir la liste des emprunts en cours pour ce livre et les afficher selon la structure d'exemple ci-dessus -->
	              </tbody>
	            </table>
	          </div>
	        </div>
	     </c:if>
      </div>
      </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
</body>
</html>