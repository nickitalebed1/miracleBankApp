export function createMockStore(state) {
    return {
        subscribe: () => {
        },
        dispatch: () => {
        },
        getState: () => {
            return {...state};
        }
    };
}