import { ref } from 'vue';
import { getAuth, onAuthStateChanged, signInWithEmailAndPassword, createUserWithEmailAndPassword } from 'firebase/auth';


class AuthService {
    constructor() {
        this.auth = getAuth();
        this.isAuthenticated = ref(false);
        this.currentUser = ref(null);

        this._initAuthStateListener();
    }

    _initAuthStateListener() {
        onAuthStateChanged(this.auth, (user) => {
            this.isAuthenticated.value = !!user;
            this.currentUser.value = user;
        });
    }

    async login(email, password) {
        try {
            const userCredential = await signInWithEmailAndPassword(this.auth, email, password);
            return userCredential.user;
        } catch (error) {
            console.error('Erro ao realizar login: ', error.message);
            throw error;
        }
    }

    async createUser(email, password) {
        try {
            const userCredential = await createUserWithEmailAndPassword(this.auth, email, password);
            return userCredential;
        } catch (error) {
            console.error('Erro ao criar usuário:', error.message);
            throw error;
        }
    }
}

export default new AuthService();