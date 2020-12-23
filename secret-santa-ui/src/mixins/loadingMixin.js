export default {
    computed: {
        isLoading: {
            get() { return this.$store.state.isLoading; },
            set(newValue) { this.$store.commit('setLoading', newValue) }
        }
    },
    /*created: function () {
        if (this.$store) {
            this.$store.commit('setLoading', false)
        }
    }*/
}