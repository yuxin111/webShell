import keymaster from 'keymaster'

const bindKeyHandler = fn => {
  return () => {
    fn()
    return false
  }
}
export default {
  bind: (seed, func) => keymaster(seed, bindKeyHandler(func)),
  ...keymaster
}
