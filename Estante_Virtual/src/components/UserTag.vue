<script setup>
import { ref } from 'vue';
import { auth } from '@/assets/js/firebase';
import { signOut, onAuthStateChanged } from 'firebase/auth';

const userName = ref('');
const isAuthenticated = ref(false);

// Função para fazer logout
const logout = async () => {
    await signOut(auth);
    isAuthenticated.value = false;
    userName.value = '';
}

onAuthStateChanged(auth, (user) => {
    if (user) {
        userName.value = user.email;
        isAuthenticated.value = true;
    } else {
        isAuthenticated.value = false;
        userName.value = '';
    }
});
</script>

<template>
    <div v-if="!isAuthenticated">
        <router-link class="nav-link" to="/authentication">
            <i class="fa-solid fa-right-to-bracket"></i> Entre
        </router-link>
    </div>

    <div v-else>
        <div class="dropdown">
            <a class="btn nav-link" type="button" data-bs-toggle="dropdown">
                <i class="fa-regular fa-user"></i> {{ userName }}
            </a>
            <ul class="dropdown-menu">
                <li>
                    <button @click="logout()" class="dropdown-item">
                        <i class="fa-solid fa-sign-out-alt"></i> Sair
                    </button>
                </li>
            </ul>
        </div>
    </div>
</template>

<style scoped>
a {
    color: var(--color_white);
    margin-right: 50px;
    padding: 10px;
    border-radius: 8px;
    transition: all 0.3s ease;

    &:hover {
        color: var(--color_white);
        background-color: var(--color_1_hover);
        transform: scale(1.1);
    }

    i {
        margin-right: 10px;
        font-size: 1.5em;
    }
}
</style>
