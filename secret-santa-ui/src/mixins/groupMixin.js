export default {
    computed: {
        groupReady: function () {
            return !this.groupReleased && this.participants && Object.keys(this.participants).length > 1
        },
        groupReleased: function () {
            return this.$store.state.group.currentGroup.released
        },
        groupEmpty: function () {
            return !this.participants || Object.keys(this.participants).length === 0
        },
        participants: function () {
            return this.$store.getters["group/participants"]
        },
    },
}