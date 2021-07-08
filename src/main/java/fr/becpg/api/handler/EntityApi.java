package fr.becpg.api.handler;

import java.util.List;
import java.util.Map;

import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityRef;

public interface EntityApi {
	
//	
//	Le paramètre format permet de modifier le format XML de la réponse :
//
//		format=xml (Par défaut)
//		format=json (Format json)) [>= 3.0]
//		format=xml_all (Contient le détails de toutes les associations)
//		format=xml_excel (Format adapté pour l'utilisation comme datasource dans excel)
//		format=xml_light (Ce format ne contient pas les associations de type enfants)
//		format=xsd (Ce format permet d'extraire la XSD)
//		format=xsd_excel (Ce format permet d'extraire la XSD pour excel)
//		des paramètres supplémentaires de filtrage pour alléger la réponse. Il y a 3 types de filtrage [>=2.0]:
//
//		Filtrage des Propriétés : inclus uniquement les propriétés listées (fields=bcpg:legalName)
//		Filtrage des Associations : inclus uniquement les associations listées (fields=bcpg:clients)
//		Filtrage des Listes : inclus uniquement les listes listées (lists=bcpg:compoList)
//		Par ailleurs, vous pouvez extraire les propriétés d'une association en passant dans le paramètre fields le nom des associations et leurs propriétés, en respectant le format suivant : ASSOC_Name1|PROP_Name1,ASSOC_Name1|PROP_Name2.
//
//		La forme négative est également supporté [>=3.1]:
//
//		lists=!bcpg:activityList
//		fields=!cm:created,!bcpg:nutListRoundedValue,!cm:modifier,!cm:creator,!cm:modifier,!cm:modified,!bcpg:entityScore,!bcpg:formulatedDate,!bcpg:illLogValue
//		Enfin pour le format JSON le paramètre params permet de passer certains paramètres à l'API [>=3.1]:
//
//		appendCode (true) : Désactive l'ajout du code beCPG
//		appendErpCode (true) : Désactive l'ajout du code ERP
//		appendMlTextConstraint (true) : Désactive l'ajout des contraintes en multilangue
//		appendNodeRef (true) : Désactive l'ajout de l'ID
//		appendDataListNodeRef (true) : Désactive l'ajout de l'ID pour les noeuds des datalists
//		appendContent (false) : Permet d'inclure le contenu des fichiers dans le JSON

	List<RemoteEntityRef> list(String query);
	List<RemoteEntityRef> list(String query, List<String> attributes,  int maxResults);
	
	RemoteEntity get(String id);
	RemoteEntity get(String id, List<String> attributes, List<String> datalists , Map<String, Boolean> params);
	
	
	void update(RemoteEntity entity);
	void update(RemoteEntity entity, boolean createversion, boolean majorVersion, String versionDescription );
	
}
