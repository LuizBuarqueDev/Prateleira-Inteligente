<template>
  <div v-if="isOpen" class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-75" @click.self="onClose">
    <div :class="modalSize" class="bg-white rounded-xl shadow-xl p-6 relative max-h-full overflow-y-auto">
      <slot />
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  isOpen: Boolean,
  onClose: Function,
  size: {
    type: String,
    default: 'md' // sm, md, lg
  }
})

const modalSize = {
  sm: 'max-w-sm w-full',
  md: 'max-w-md w-full',
  lg: 'max-w-2xl w-full'
}[props.size]

console.log('Modal aberto?', props.isOpen)
</script>

<style scoped>
/* Fundo escuro */
.bg-black.bg-opacity-75 {
  background-color: rgba(0, 0, 0, 0.75); /* Fundo escuro */
}

/* Centralizando o modal no meio da tela */
.fixed {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 50;
}

.max-w-sm {
  max-width: 400px;
}

.max-w-md {
  max-width: 600px;
}

.max-w-2xl {
  max-width: 800px;
}

/* Opções de hover no botão de fechar */
button:hover {
  cursor: pointer;
}
</style>
