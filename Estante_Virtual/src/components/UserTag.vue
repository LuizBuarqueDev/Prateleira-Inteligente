<script setup>
import { getAuth, signOut } from 'firebase/auth';
import { onMounted, ref } from 'vue';

const auth = getAuth();
const userName = ref('');

const logout = async () => {
    await signOut(auth);
}

onMounted(() => {
    userName.value = auth.currentUser.email;
});
</script>

<template>
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
}
</style>
