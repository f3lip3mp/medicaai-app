const admin = require('firebase-admin');
const functions = require('firebase-functions');

// Inicializa o Firebase Admin SDK
admin.initializeApp();

// Função para atribuir papel de admin a um usuário
exports.setAdminRole = functions.https.onCall((data, context) => {
    // Verifique se o usuário está autenticado e se é um admin
    if (!context.auth || context.auth.token.role !== 'admin') {
        throw new functions.https.HttpsError('permission-denied', 'Somente admins podem atribuir papéis');
    }

    const uid = data.uid;  // UID do usuário a ser promovido

    // Atribui o papel de admin ao usuário
    return admin.auth().setCustomUserClaims(uid, { admin: true })
        .then(() => {
            return { message: 'Papel de admin atribuído com sucesso.' };
        })
        .catch((error) => {
            throw new functions.https.HttpsError('unknown', error.message);
        });
});
