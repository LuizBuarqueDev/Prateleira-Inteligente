<script setup>
import AuthService from '@/services/AuthService';
import { computed } from 'vue';

const userName = computed(() => AuthService.currentUser.value?.email || '');
const isAuthenticated = computed(() => AuthService.isAuthenticated.value);

const logout = async () => {
    try {
        await AuthService.logout();
    } catch (error) {
        console.error('Erro ao realizar logout:', error.message);
    }
};
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
                    <button @click="logout" class="dropdown-item">
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
