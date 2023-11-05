export const GITHUB_CLIENT_ID="098e442d98a100074b33";
export const GITHUB_SCOPE="public_repo";

export const ATLASSIAN_CLIENT_ID = "C1kBvza9tIkMGi6ZJKERuzoGrjoBjkhX";
export const ATLASSIAN_URL=`https://auth.atlassian.com/authorize?audience=api.atlassian.com&client_id=${ATLASSIAN_CLIENT_ID}&scope=write%3Ajira-work%20read%3Ajira-work%20manage%3Ajira-project%20manage%3Ajira-configuration%20read%3Ajira-user%20manage%3Ajira-data-provider%20manage%3Ajira-webhook&response_type=code&prompt=consent`;
// https://auth.atlassian.com/authorize?audience=api.atlassian.com&client_id=C1kBvza9tIkMGi6ZJKERuzoGrjoBjkhX&scope=write%3Ajira-work%20read%3Ajira-work%20manage%3Ajira-project%20manage%3Ajira-configuration%20read%3Ajira-user%20manage%3Ajira-data-provider%20manage%3Ajira-webhook&redirect_uri=https%3A%2F%2Foauth.pstmn.io%2Fv1%2Fcallback&state=${YOUR_USER_BOUND_VALUE}&response_type=code&prompt=consent