<script setup>
import BaseFooter from "@/components/base_layout/BaseFooter.vue";
import BaseNav from "@/components/base_layout/BaseNav.vue";
</script>

<template>
  <div>
    <BaseNav />

    <main>
      <slot>
        <!-- O conteúdo ficará aqui dentro -->
      </slot>
    </main>

    <BaseFooter />
  </div>
</template>


<style scoped></style>
