export const processResponse = async (response) => {
  if (response.ok) {
    if (response.status === 204) return null;
    const text = await response.text();
    if (!text) return null;
    try { return JSON.parse(text); } catch { return text; }
  }
  const msg = await response.text().catch(() => '');
  throw new Error(msg || `Error ${response.status} ${response.statusText}`);
};