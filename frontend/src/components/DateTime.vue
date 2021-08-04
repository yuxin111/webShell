<template>
  <el-date-picker
    v-model="dateTimeValue"
    type="datetimerange"
    :picker-options="pickerOptions"
    value-format="yyyy-MM-dd HH:mm:ss"
    range-separator="至"
    start-placeholder="开始时间"
    end-placeholder="结束时间"
    size="small"
    align="right">
  </el-date-picker>
</template>

<script>
export default {
  name: 'DateTime',
  props: {
    value: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      dateTimeValue: '',
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      }
    }
  },
  watch: {
    value (value) {
      this.dateTimeValue = value
    },
    dateTimeValue (dateTimeValue) {
      this.$emit('input', dateTimeValue)
    }
  }
}
</script>

<style scoped>

</style>
