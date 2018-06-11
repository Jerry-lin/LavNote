var MSG={"app":"Leanote","share":"Partager","noTag":"Pas d'étiquettes","inputUsername":"Entrer le nom d'utilisateur","inputEmail":"L'adresse courriel est requis","inputPassword":"Mot de passe requis","inputPassword2":"Veuillez rentrer le nouveau mot de passe à nouveau.","confirmPassword":"Le mot de passe ne correspond pas.","history":"Historique","editorTips":"Astuces","editorTipsInfo":"<h4>1. Raccourcis</h4>ctrl+maj+c Active/désactive le code<h4>2. maj+entrée Sortir du bloc courant</h4> ex. <img src=\"/images/outofcode.png\" style=\"width: 90px\"/> dans cette situation vous pouvez utiliser maj+entrée pour sortir du bloc de code courant.","all":"Le plus récent","trash":"Corbeille","delete":"Effacer","unTitled":"Sans titre","defaultShare":"Partage par défaut","writingMode":"Mode écriture","normalMode":"Mode normal","saving":"Sauvegarde","saveSuccess":"Sauvegarde réussie","update":"Mettre à jour","close":"Fermer","cancel":"Annuler","send":"Envoyer","shareToFriends":"Partage avec ses amis","publicAsBlog":"Publier en tant que blog","cancelPublic":"Annuler la publication","move":"Déplacer","copy":"Copier","rename":"Renommer","addChildNotebook":"Ajouer un bloc-note enfant","deleteAllShared":"Effacer l'utilisateur partagé","deleteSharedNotebook":"Effacer le bloc-notes partagé","copyToMyNotebook":"Copier vers mon bloc-notes","checkEmail":"Vérifier courriel","sendVerifiedEmail":"Envoyer le courriel de confirmation","friendEmail":"Courriel de l'ami","readOnly":"Lecture seule","writable":"Editable","inputFriendEmail":"Le courriel de votre ami est requis","clickToChangePermission":"Cliquez pour changer l'autorisation.","sendInviteEmailToYourFriend":"Envoyer un courriel d'invitation à votre ami.","friendNotExits":"Votre ami n'a pas de compte %s, lien d'invitation à s'enregistrer: %s","emailBodyRequired":"Corps du message requis","sendSuccess":"succès","inviteEmailBody":"Coucou, je suis %s, %s est génial, viens!","historiesNum":"Nous avons enregistré au maximum <b>10</b> historiques récents de chaque note.","noHistories":"Pas d'historique.","fold":"Plier","unfold":"Déplier","datetime":"Date & Heure","restoreFromThisVersion":"Restaurer depuis cette version","confirmBackup":"Êtes-vous sûr de vouloir restaurer depuis cette version? Nous allons réaliser une copie de sauvegarde de la note actuelle.","createAccountSuccess":"Compte créé avec succès","createAccountFailed":"Echec de la création du compte","updateUsernameSuccess":"Mise à jour du nom d'utilisateur réussie","usernameIsExisted":"Le nom d'utilisateur existe déjà.","noSpecialChars":"le nom d'utilisateur ne peut pas contenir de caractères spéciaux","minLength":"La longueur doit être d'au moins %s","errorEmail":"Veuillez renseigner le courriel correct","verifiedEmaiHasSent":"Le courriel de confirmation a été envoyé, veuillez surveiller votre boite de réception.","emailSendFailed":"Envoi du courriel échoué.","inputNewPassword":"Le nouveau mot de passe est requis.","errorPassword":"La taille du mot de passe est d'au moins 6 caractères et aussi complexe que possible.","updatePasswordSuccess":"Mises à jour du mot de passe réussie.","Please save note firstly!":"Veuillez d'abord enregistrer votre note!","Please sign in firstly!":"Veuillez d'abord vous enregistrer!","Are you sure ?":"Êtes-vous sûr ?","Are you sure to install it ?":"Voulez-vous vraiment l'installer ?","Are you sure to delete":"Voulez-vous vraiment l'effacer?","Success":"Succès","Error":"Erreur","File exists":"Le fichier exite","Delete file":"Effacer le fichier","No images":"Pas d'images","Filename":"Nom de fichier","Group Title":"Titre du groupe","Hyperlink":"Hyperlien","Please provide the link URL and an optional title":"Veuillez fournir le lien hypertexte et un titre facultatif","optional title":"titre facultatif","Cancel":"Annuler","Strong":"Fort","strong text":"Texte fort","Emphasis":"Emphase","emphasized text":"Texte avec emphase","Blockquote":"Bloc de citation","Code Sample":"Extrait de code","enter code here":"entrer le code ici","Image":"Image","Heading":"Titre","Numbered List":"Liste ordonnée","Bulleted List":"Liste non ordonnée","List item":"Element de liste","Horizontal Rule":"Ligne horizontale","Markdown syntax":"Syntaxe markdown","Undo":"Annuler","Redo":"Reproduire","enter image description here":"entrer la description de l'image ici","enter link description here":"entrer la description du lien ici","Edit mode":"Mode édition","Vim mode":"Mode Vim","Emacs mode":"Mode Emacs","Normal mode":"Mode Normal","Normal":"Normal","Light":"Léger","Light editor":"Editeur léger","Add Album":"Ajouter un album","Cannot delete default album":"Album par défaut impossible à effacer","Cannot rename default album":"Album par défaut impossible à renommer","Rename Album":"Renommer un album","Add Success!":"Ajout effectué avec succès!","Rename Success!":"Renommage effectué avec succès!","Delete Success!":"Effaçage effectué avec succès!","Are you sure to delete this image ?":"Voulez-vous vraiment effacer cette image ?","click to remove this image":"cliquez pour supprimer cette image","error":"erreur","Prev":"Préc","Next":"Suiv"};function getMsg(key, data) {var msg = MSG[key];if(msg) {if(data) {if(!isArray(data)) {data = [data];}for(var i = 0; i < data.length; ++i) {msg = msg.replace("%s", data[i]);}}return msg;}return key;}