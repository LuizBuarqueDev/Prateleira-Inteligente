import { ref } from 'vue';
import { getAuth, onAuthStateChanged, signInWithEmailAndPassword, createUserWithEmailAndPassword, signOut } from 'firebase/auth';

import router from '@/router';


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
            router.push('/');
            return userCredential.user;
        } catch (error) {
            alert('Erro ao realizar login: ', error.message);
            throw error;
        }
    }

    async createUser(email, password) {
        try {
            const userCredential = await createUserWithEmailAndPassword(this.auth, email, password);
            router.push('/');
            return userCredential;
        } catch (error) {
            alert('Erro ao criar usuário:', error.message);
            throw error;
        }
    }

    async logout() {
        try {
            await signOut(this.auth);
            this.isAuthenticated.value = false;
            this.currentUser.value = null;
            router.push('/');

        } catch (error) {
            alert('Erro ao realizar logout: ', error.message);
            throw error;
        }
    }
}

export default new AuthService();